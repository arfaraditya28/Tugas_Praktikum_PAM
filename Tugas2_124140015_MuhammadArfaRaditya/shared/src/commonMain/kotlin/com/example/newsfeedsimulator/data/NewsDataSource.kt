package com.example.newsfeedsimulator.data

import com.example.newsfeedsimulator.model.News
import com.example.newsfeedsimulator.model.NewsCategory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Sumber data simulasi lokal. Tidak memakai API internet.
 *
 * newsFeedFlow() adalah inti requirement Flow:
 * - memakai flow builder -> flow { }
 * - memakai emit() untuk mengeluarkan 1 berita
 * - memakai delay(2000) agar 1 berita baru tiap 2 detik
 * - memakai collect() di sisi ViewModel
 */
object NewsDataSource {

    val sampleNews: List<News> = listOf(
        News(
            id = 1,
            title = "AI Generatif Semakin Canggih di 2026",
            category = NewsCategory.TEKNOLOGI,
            summary = "Model AI terbaru mampu memahami konteks lebih panjang dan multimodal.",
            content = "Model AI generatif terbaru tahun 2026 mampu memproses teks, gambar, dan suara dalam satu konteks yang sangat panjang. Para peneliti menilai kemampuan penalarannya meningkat signifikan dibanding generasi sebelumnya, sehingga cocok untuk asisten riset, pendidikan, dan industri kreatif. Namun, isu privasi data dan kebutuhan komputasi besar masih menjadi tantangan utama yang harus diselesaikan.",
            timestamp = "08:00"
        ),
        News(
            id = 2,
            title = "Timnas Lolos ke Final Setelah Adu Penalti Dramatis",
            category = NewsCategory.OLAHRAGA,
            summary = "Kemenangan dramatis lewat adu penalti 5-4 membawa timnas ke final.",
            content = "Pertandingan semifinal berlangsung ketat hingga babak tambahan waktu dengan skor imbang 2-2. Kiper timnas menjadi pahlawan setelah menggagalkan dua tendangan penalti lawan. Pelatih memuji mental para pemain muda yang tetap tenang di bawah tekanan puluhan ribu suporter. Final akan digelar akhir pekan ini di stadion utama.",
            timestamp = "08:05"
        ),
        News(
            id = 3,
            title = "Film Lokal Pecahkan Rekor Penonton",
            category = NewsCategory.HIBURAN,
            summary = "Film drama lokal menembus 5 juta penonton dalam dua minggu.",
            content = "Film drama produksi dalam negeri berhasil menembus 5 juta penonton hanya dalam dua minggu penayangan. Cerita yang dekat dengan kehidupan sehari-hari dan akting para pemainnya dipuji kritikus. Produser mengumumkan sekuel akan mulai diproduksi tahun depan dengan skala yang lebih besar dan lokasi syuting di tiga kota.",
            timestamp = "08:10"
        ),
        News(
            id = 4,
            title = "Parlemen Sahkan UU Perlindungan Data Digital",
            category = NewsCategory.POLITIK,
            summary = "Aturan baru mewajibkan perusahaan menjaga data pengguna lebih ketat.",
            content = "Parlemen resmi mengesahkan undang-undang perlindungan data digital setelah pembahasan panjang. Aturan ini mewajibkan perusahaan teknologi meminta persetujuan eksplisit sebelum mengolah data pribadi, melaporkan kebocoran data maksimal 72 jam, dan menunjuk petugas perlindungan data. Pelanggaran berat dapat dikenai denda hingga 4% dari omzet tahunan perusahaan.",
            timestamp = "08:15"
        ),
        News(
            id = 5,
            title = "IHSG Menguat di Tengah Optimisme Ekonomi",
            category = NewsCategory.EKONOMI,
            summary = "Indeks saham menguat 1,2% didorong sektor perbankan dan energi.",
            content = "Indeks Harga Saham Gabungan (IHSG) menguat 1,2% pada perdagangan hari ini. Penguatan didorong oleh saham perbankan dan energi di tengah optimisme pemangkasan suku bunga. Analis menilai arus modal asing mulai kembali masuk, namun mengingatkan investor tetap waspada terhadap sentimen global dan harga komoditas yang fluktuatif.",
            timestamp = "08:20"
        ),
        News(
            id = 6,
            title = "Smartphone Lipat Makin Tipis dan Tahan Lama",
            category = NewsCategory.TEKNOLOGI,
            summary = "Generasi baru HP lipat hadir dengan engsel lebih kuat dan baterai awet.",
            content = "Vendor smartphone merilis generasi baru ponsel lipat yang lebih tipis, ringan, dan diklaim tahan 500 ribu kali lipatan. Layarnya kini lebih cerah dan minim bekas lipatan. Baterai berkapasitas besar dipadukan chip hemat daya sehingga mampu bertahan seharian penuh untuk pemakaian berat. Harganya diprediksi lebih terjangkau dibanding tahun lalu.",
            timestamp = "08:25"
        ),
        News(
            id = 7,
            title = "Atlet Muda Raih Emas Kejuaraan Atletik Asia",
            category = NewsCategory.OLAHRAGA,
            summary = "Pelari 100 meter catatkan waktu terbaik nasional 10,21 detik.",
            content = "Atlet muda Indonesia meraih medali emas nomor 100 meter pada kejuaraan atletik Asia dengan catatan waktu 10,21 detik, sekaligus memecahkan rekor nasional. Pelatih menyebut program latihan berbasis sport science dan nutrisi ketat menjadi kunci. Atlet tersebut kini dipersiapkan menuju Olimpiade berikutnya.",
            timestamp = "08:30"
        ),
        News(
            id = 8,
            title = "Konser Musik Virtual Ditonton Jutaan Penonton",
            category = NewsCategory.HIBURAN,
            summary = "Konser virtual dengan teknologi XR sukses digelar semalam.",
            content = "Konser musik virtual yang memakai teknologi extended reality (XR) sukses digelar dan ditonton jutaan penonton dari 40 negara. Penonton bisa memilih sudut kamera, berinteraksi lewat avatar, dan membeli merchandise digital. Promotor menyebut format hybrid daring-luring akan menjadi tren industri hiburan ke depan.",
            timestamp = "08:35"
        ),
        News(
            id = 9,
            title = "Pemerintah Salurkan Bantuan untuk UMKM Digital",
            category = NewsCategory.POLITIK,
            summary = "Program baru bantu 100 ribu UMKM go-digital lewat pelatihan dan modal.",
            content = "Pemerintah meluncurkan program bantuan untuk 100 ribu pelaku UMKM agar bisa go-digital. Bantuan meliputi pelatihan pemasaran online, pendampingan pencatatan keuangan, dan akses pembiayaan berbunga rendah. Program ini ditargetkan meningkatkan omzet UMKM peserta hingga 30% dalam setahun.",
            timestamp = "08:40"
        ),
        News(
            id = 10,
            title = "Harga Emas Antam Naik, Investor Ritel Antusias",
            category = NewsCategory.EKONOMI,
            summary = "Harga emas batangan naik Rp15.000 per gram pagi ini.",
            content = "Harga emas batangan naik Rp15.000 per gram pada perdagangan pagi ini. Kenaikan dipicu ketidakpastian global sehingga investor beralih ke aset aman. Gerai penjualan melaporkan antrean pembeli ritel meningkat. Analis menyarankan strategi beli bertahap (dollar cost averaging) bagi investor jangka panjang.",
            timestamp = "08:45"
        ),
        News(
            id = 11,
            title = "Satelit Komunikasi Baru Sukses Mengorbit",
            category = NewsCategory.TEKNOLOGI,
            summary = "Satelit baru akan perluas internet cepat ke daerah 3T.",
            content = "Satelit komunikasi terbaru sukses mengorbit dan akan beroperasi penuh bulan depan. Satelit ini membawa kapasitas bandwidth besar untuk memperluas layanan internet cepat ke daerah tertinggal, terdepan, dan terluar (3T). Kecepatan unduh di daerah percontohan disebut mencapai 100 Mbps.",
            timestamp = "08:50"
        ),
        News(
            id = 12,
            title = "Liga Basket Musim Baru Gunakan Format Baru",
            category = NewsCategory.OLAHRAGA,
            summary = "Format playoff diperluas, jumlah pertandingan bertambah.",
            content = "Operator liga basket mengumumkan format baru untuk musim depan. Jumlah tim playoff diperluas dari 8 menjadi 12 tim, sehingga persaingan lebih ketat. Setiap tim juga wajib memainkan minimal dua pemain muda di setiap pertandingan sebagai bagian dari program regenerasi.",
            timestamp = "08:55"
        ),
        News(
            id = 13,
            title = "Serial Komedi Terbaru Jadi Perbincangan Warganet",
            category = NewsCategory.HIBURAN,
            summary = "Serial 8 episode itu trending berkat dialog yang relatable.",
            content = "Serial komedi terbaru dengan 8 episode menjadi perbincangan hangat warganet. Dialognya yang relatable tentang kehidupan pekerja kantoran banyak dijadikan meme. Platform streaming mengonfirmasi musim kedua sudah dalam tahap penulisan naskah karena tingginya jumlah penonton.",
            timestamp = "09:00"
        ),
        News(
            id = 14,
            title = "Pilkada Serentak Berjalan Aman dan Kondusif",
            category = NewsCategory.POLITIK,
            summary = "Tingkat partisipasi pemilih mencapai 78% menurut data sementara.",
            content = "Pemungutan suara pilkada serentak di ratusan daerah berjalan aman dan kondusif. Data sementara menunjukkan tingkat partisipasi pemilih mencapai 78%. Pengamat menilai sosialisasi masif lewat media sosial berhasil menjangkau pemilih muda. Hasil resmi akan diumumkan setelah rekapitulasi berjenjang selesai.",
            timestamp = "09:05"
        ),
        News(
            id = 15,
            title = "Rupiah Menguat ke Level Terbaik Tiga Bulan",
            category = NewsCategory.EKONOMI,
            summary = "Rupiah menguat didorong surplus neraca dagang.",
            content = "Nilai tukar rupiah menguat ke level terbaik dalam tiga bulan terakhir. Penguatan didorong surplus neraca dagang dan melemahnya indeks dolar AS. Bank sentral menyatakan akan terus menjaga stabilitas nilai tukar lewat intervensi terukur dan pendalaman pasar keuangan.",
            timestamp = "09:10"
        )
    )

    /**
     * Flow dingin (cold flow) yang mengeluarkan 1 berita tiap 2 detik,
     * berputar terus (indeks modulo) agar simulasi tidak berhenti.
     */
    fun newsFeedFlow(): Flow<News> = flow {
        var index = 0
        while (true) {
            val news = sampleNews[index % sampleNews.size]
            emit(news) // <-- emit(): mengeluarkan 1 item ke collector
            index++
            delay(2_000) // <-- simulasi berita baru tiap 2 detik
        }
    }

    fun findById(id: Int): News? = sampleNews.find { it.id == id }
}
