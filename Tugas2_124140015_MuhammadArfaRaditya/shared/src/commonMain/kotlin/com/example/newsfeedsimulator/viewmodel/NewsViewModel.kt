package com.example.newsfeedsimulator.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsfeedsimulator.model.News
import com.example.newsfeedsimulator.model.NewsCategory
import com.example.newsfeedsimulator.model.NewsUiModel
import com.example.newsfeedsimulator.model.toUiModel
import com.example.newsfeedsimulator.repository.NewsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

/**
 * State holder untuk UI. Memenuhi requirement:
 * - Flow: filter + map + onEach + catch + collect
 * - StateFlow: MutableStateFlow private, ekspos read-only via asStateFlow()
 * - Coroutines: viewModelScope.launch, suspend, async/await, delay (di data source/repo)
 */
class NewsViewModel(
    private val repository: NewsRepository = NewsRepository()
) : ViewModel() {

    // ---- Daftar berita (hasil collect Flow, sudah berbentuk UiModel) ----
    private val _newsList = MutableStateFlow<List<NewsUiModel>>(emptyList())
    val newsList: StateFlow<List<NewsUiModel>> = _newsList.asStateFlow()

    // ---- Kategori yang dipilih (null = Semua) ----
    private val _selectedCategory = MutableStateFlow<NewsCategory?>(null)
    val selectedCategory: StateFlow<NewsCategory?> = _selectedCategory.asStateFlow()

    // ---- Jumlah berita yang sudah dibaca (StateFlow inti tugas) ----
    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    // id yang sudah dihitung agar tidak double-count
    private val alreadyReadIds = mutableSetOf<Int>()

    // ---- Detail berita ----
    private val _selectedDetail = MutableStateFlow<News?>(null)
    val selectedDetail: StateFlow<News?> = _selectedDetail.asStateFlow()

    private val _readEstimate = MutableStateFlow("")
    val readEstimate: StateFlow<String> = _readEstimate.asStateFlow()

    private val _isDetailLoading = MutableStateFlow(false)
    val isDetailLoading: StateFlow<Boolean> = _isDetailLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isStreaming = MutableStateFlow(true)
    val isStreaming: StateFlow<Boolean> = _isStreaming.asStateFlow()

    private var streamJob: Job? = null
    private var detailJob: Job? = null

    init {
        startStreaming()
    }

    /**
     * Alur yang dinilai:
     * Data source -> Flow -> onEach -> filter(kategori) -> map(ke UiModel)
     *   -> catch(error) -> collect(tampilkan di UI)
     */
    fun startStreaming() {
        streamJob?.cancel()
        _isStreaming.value = true
        streamJob = viewModelScope.launch {
            try {
                repository.getNewsStream()
                    .onEach {
                        // Efek samping ringan tiap berita lewat (mis. log).
                        // Tidak mengubah data, hanya observasi.
                    }
                    .filter { news ->
                        // FILTER: hanya teruskan berita sesuai kategori pilihan.
                        // null berarti "Semua".
                        val chosen = _selectedCategory.value
                        chosen == null || news.category == chosen
                    }
                    .map { news ->
                        // TRANSFORM: ubah News mentah jadi format siap tampil.
                        news.toUiModel()
                    }
                    .catch { e ->
                        // ERROR HANDLING level Flow.
                        if (e is CancellationException) throw e
                        _errorMessage.value = "Gagal memuat feed: ${e.message}"
                    }
                    .collect { uiModel ->
                        // COLLECT: append ke daftar, hindari duplikat id berurutan,
                        // batasi 30 item agar memori aman.
                        val current = _newsList.value
                        if (current.isEmpty() || current.last().id != uiModel.id) {
                            _newsList.value = (current + uiModel).takeLast(30)
                        }
                    }
            } catch (e: CancellationException) {
                throw e // cancellation normal, bukan error
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.message}"
            } finally {
                _isStreaming.value = false
            }
        }
    }

    fun stopStreaming() {
        streamJob?.cancel()
        _isStreaming.value = false
    }

    /** Ganti kategori -> restart stream agar operator filter langsung terlihat efeknya. */
    fun selectCategory(category: NewsCategory?) {
        if (_selectedCategory.value == category) return
        _selectedCategory.value = category
        _newsList.value = emptyList()
        _errorMessage.value = null
        startStreaming()
    }

    /**
     * Dipanggil saat user klik card berita:
     * coroutine -> async/await paralel -> delay di repo -> tampil detail.
     */
    fun openNewsDetail(id: Int) {
        detailJob?.cancel() // cancellation: batalkan klik sebelumnya yg belum selesai
        detailJob = viewModelScope.launch {
            _isDetailLoading.value = true
            _errorMessage.value = null
            try {
                // async/await: dua suspend function jalan paralel.
                val detailDeferred = async { repository.fetchNewsDetail(id) }
                val estimateDeferred = async { repository.fetchReadEstimate(id) }

                val detail = detailDeferred.await()
                val estimate = estimateDeferred.await()

                _selectedDetail.value = detail
                _readEstimate.value = estimate

                // Tambah counter hanya sekali per berita unik.
                if (alreadyReadIds.add(detail.id)) {
                    _readCount.value = _readCount.value + 1
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                _errorMessage.value = "Gagal memuat detail: ${e.message}"
            } finally {
                _isDetailLoading.value = false
            }
        }
    }

    fun closeDetail() {
        detailJob?.cancel()
        _selectedDetail.value = null
        _readEstimate.value = ""
        _isDetailLoading.value = false
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
