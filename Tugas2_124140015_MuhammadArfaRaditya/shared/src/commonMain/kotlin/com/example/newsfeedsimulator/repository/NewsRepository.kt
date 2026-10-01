package com.example.newsfeedsimulator.repository

import com.example.newsfeedsimulator.data.NewsDataSource
import com.example.newsfeedsimulator.model.News
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach

/**
 * Repository: jembatan antara data source dan ViewModel.
 * Semua kode di commonMain sehingga berlaku untuk Android & iOS (konsep KMP).
 */
class NewsRepository {

    /**
     * Mengekspos Flow berita dengan error handling via catch().
     * onEach dipakai untuk logging/efek samping ringan tiap item lewat.
     */
    fun getNewsStream(): Flow<News> =
        NewsDataSource.newsFeedFlow()
            .onEach { /* efek samping ringan: bisa untuk log/analytics */ }
            .catch { e ->
                // Error handling sederhana di level Flow.
                // Kita lempar ulang agar ViewModel bisa menampilkan pesan error.
                throw e
            }

    /**
     * Suspend function: simulasi pengambilan detail berita secara asynchronous.
     * delay() meniru latency jaringan/database.
     */
    suspend fun fetchNewsDetail(id: Int): News {
        delay(1_500) // simulasi loading jaringan
        return NewsDataSource.findById(id)
            ?: throw IllegalArgumentException("Berita dengan id=$id tidak ditemukan")
    }

    /**
     * Suspend function kedua agar bisa didemokan async/await paralel
     * (diambil bersamaan dengan fetchNewsDetail di ViewModel).
     */
    suspend fun fetchReadEstimate(id: Int): String {
        delay(500) // kerja ringan paralel
        return "${(id % 3) + 2} mnt baca"
    }
}
