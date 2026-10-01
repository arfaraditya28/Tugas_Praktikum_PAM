# NewsFeedSimulator

Aplikasi **News Feed Simulator** — tugas praktikum Pertemuan 2
"Advanced Kotlin, Coroutines, dan Flow".

Aplikasi Kotlin Multiplatform (berbagi UI Compose Multiplatform) yang
mensimulasikan feed berita real-time: **1 berita baru setiap 2 detik**,
bisa difilter per kategori, diklik untuk membaca detail (async), dan
menghitung jumlah berita yang sudah dibaca.

## Fitur

- Header "News Feed Simulator" + subjudul + status Live/Paused
- Counter **"Berita Dibaca: X"** (StateFlow)
- Filter kategori: Semua, Teknologi, Olahraga, Hiburan, Politik, Ekonomi
- Daftar berita dalam Card rounded-corners (judul, kategori, ringkasan, waktu)
- Feed otomatis bertambah tiap 2 detik (Flow + delay)
- Tombol Pause/Resume untuk menghentikan/menjalankan simulasi
- Klik card → loading → detail berita (judul, kategori, isi, waktu, estimasi baca)
- Tombol Kembali / Selesai Baca
- Error handling sederhana (Flow `catch()` + try-catch + Snackbar)
- Tema ungu modern (light), layout responsive (LazyColumn/LazyRow)

## Teknologi

- Kotlin Multiplatform + Compose Multiplatform (semua logika di `commonMain`)
- Kotlin Coroutines (`launch`, `async`/`await`, `delay`, `Job` cancellation)
- Kotlin Flow (`flow`, `emit`, `collect`, `filter`, `map`, `onEach`, `catch`)
- StateFlow (`MutableStateFlow` private + `asStateFlow()` read-only)
- AndroidX Lifecycle ViewModel (`viewModelScope`, `viewModel()`)
- Material3 Compose components
- Data simulasi lokal — **tanpa API internet**

## Struktur Project

```
Tugas2_124140015_MuhammadArfaRaditya/
├── shared/src/commonMain/kotlin/com/example/newsfeedsimulator/
│   ├── App.kt                    # Entry Compose: pilih Feed vs Detail
│   ├── model/News.kt             # News, NewsCategory, NewsUiModel + toUiModel()
│   ├── data/NewsDataSource.kt    # 15 berita simulasi + newsFeedFlow() (flow/emit/delay)
│   ├── repository/NewsRepository.kt  # getNewsStream() + suspend fetchNewsDetail()
│   ├── viewmodel/NewsViewModel.kt    # filter/map/onEach/catch/collect,
│   │                             # StateFlow readCount, async/await detail
│   └── ui/
│       ├── NewsTheme.kt          # Skema warna ungu
│       ├── NewsFeedScreen.kt     # Header, counter, filter chips, daftar card
│       └── NewsDetailScreen.kt   # Loading + isi detail + tombol kembali
├── androidApp/                   # MainActivity (setContent { App() })
└── iosApp/                       # Entry iOS (MainViewController)
```

## Cara Menjalankan

Butuh Android Studio (Ladybug+) + JDK 17 + Android SDK + Gradle wrapper bawaan.

```bash
cd "Tugas2_124140015_MuhammadArfaRaditya"

# Build APK debug Android
./gradlew :androidApp:assembleDebug

# Atau langsung run dari Android Studio:
# Run ▶ → konfigurasi "androidApp"
```

Untuk iOS: buka folder `iosApp` di Xcode → Run.

## Bagaimana Flow Digunakan

`data/NewsDataSource.kt`:

```text
fun newsFeedFlow(): Flow<News> = flow {   // 1. flow builder
    var index = 0
    while (true) {
        emit(sampleNews[index % sampleNews.size]) // 2. emit 1 berita
        index++
        delay(2_000) // 3. jeda 2 detik → berita baru tiap 2 detik
    }
}
```

`viewmodel/NewsViewModel.kt` meng-collect-nya:

```text
repository.getNewsStream()
    .onEach { /* observasi tiap item lewat */ }
    .filter { news -> chosen == null || news.category == chosen }
    .map { news -> news.toUiModel() }
    .catch { e -> _errorMessage.value = "..." }
    .collect { uiModel -> _newsList.value = ... }
```

## Penjelasan filter / map

- **filter**: `NewsViewModel.startStreaming()` memakai operator
  `filter { }` — hanya berita yang kategorinya sama dengan pilihan user
  yang diteruskan. Ganti kategori → list dikosongkan + stream di-restart
  sehingga efek filter langsung terlihat.
- **map**: operator `map { news.toUiModel() }` mengubah `News` mentah
  menjadi `NewsUiModel` (presentation model: `displayTitle`,
  `categoryLabel`, `timeLabel`) yang siap ditampilkan di Card.
- **onEach**: dipakai di `NewsRepository.getNewsStream()` dan di ViewModel
  sebagai efek samping ringan (tempat log/analytics) tanpa mengubah data.

## Penjelasan StateFlow

```text
private val _readCount = MutableStateFlow(0)  // internal, bisa diubah
val readCount: StateFlow<Int> = _readCount.asStateFlow() // publik, read-only
```

Pola yang sama dipakai untuk `newsList`, `selectedCategory`,
`selectedDetail`, `isDetailLoading`, `errorMessage`, `isStreaming`.
UI membaca via `collectAsState()` sehingga otomatis recompose saat nilai berubah.
Counter bertambah 1 setiap user berhasil membuka detail berita unik
(dijaga `alreadyReadIds` agar tidak double-count).

## Penjelasan Coroutine async/await

Klik card → `NewsViewModel.openNewsDetail(id)`:

```text
detailJob = viewModelScope.launch {          // coroutine
    _isDetailLoading.value = true
    val detailDeferred = async { repository.fetchNewsDetail(id) }  // suspend + delay(1500)
    val estimateDeferred = async { repository.fetchReadEstimate(id) } // suspend + delay(500)
    val detail = detailDeferred.await()      // tunggu hasil paralel
    val estimate = estimateDeferred.await()
    _selectedDetail.value = detail           // tampilkan di UI
}
```

- `suspend function` + `delay()` ada di `NewsRepository`
  (simulasi latency jaringan).
- `async`/`await` menjalankan dua fetch secara paralel.
- Klik baru membatalkan job lama (`detailJob?.cancel()` = cancellation).
- Error ditangkap try-catch; pembatalan (`CancellationException`)
  dilempar ulang agar tidak dianggap error.

## Cara Kerja Simulasi Tiap 2 Detik

1. `sampleNews` berisi 15 berita statis (5 kategori × 3 berita).
2. `newsFeedFlow()` loop `while (true)`, tiap iterasi `emit()` 1 berita
   lalu `delay(2000)`.
3. Indeks modulo (`% size`) membuat daftar berputar terus tanpa berhenti.
4. ViewModel `collect()` tiap item → transform `map` → append ke
   `_newsList` (max 30 item terakhir).
5. UI LazyColumn menampilkan list terbalik (terbaru di atas);
   tombol Pause memanggil `stopStreaming()` (cancel `streamJob`),
   Resume memanggil `startStreaming()` lagi.
