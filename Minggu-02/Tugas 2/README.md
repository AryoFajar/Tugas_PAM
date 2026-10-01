# News Feed Simulator

Tugas Praktikum — Pertemuan 2: Advanced Kotlin, Coroutines, dan Flow
Pengembangan Aplikasi Mobile — Institut Teknologi Sumatera

## Deskripsi

Aplikasi simulasi *news feed* sederhana yang mendemonstrasikan penggunaan
**Kotlin Coroutines** dan **Kotlin Flow** dalam skenario nyata: menerima
data berita baru secara berkala, memfilter berdasarkan kategori,
mengubah formatnya, menyimpan status (jumlah dibaca) secara reaktif,
dan mengambil detail berita secara asynchronous.

## Fitur

| # | Fitur | Implementasi |
|---|-------|---------------|
| 1 | Flow berita baru setiap 2 detik | `NewsRepository.newsFeed()` — `flow { }` + `delay(2000)` + `emit()` |
| 2 | Filter berita per kategori | Operator `.filter { }` di `NewsFeedManager.startObserving()` |
| 3 | Transform ke format tampilan | Operator `.map { }` mengubah `News` → `DisplayNews` |
| 4 | StateFlow jumlah dibaca | `NewsFeedManager.readCount: StateFlow<Int>` |
| 5 | Ambil detail berita secara async | `NewsFeedManager.fetchDetailsAsync()` — `async` / `await` di `Dispatchers.IO` |
| Bonus | Error handling | Operator `.catch { }` pada Flow + `try-catch` di coroutine `async` |
| Bonus | Unit test | `NewsFeedManagerTest.kt` menggunakan `runTest` dari `kotlinx-coroutines-test` |

## Struktur Proyek

```
TugasPraktikum2/
├── androidApp/
├── desktopApp/
└── shared/
    └── src/
        ├── commonMain/kotlin/com/example/tugaspraktikum2/newsfeed/
        │   ├── model/News.kt                 
        │   ├── repository/NewsRepository.kt     
        │   ├── manager/NewsFeedManager.kt      
        │   └── Main.kt                         
        └── commonTest/kotlin/com/example/tugaspraktikum2/newsfeed/
            └── NewsFeedManagerTest.kt           
```

## Cara Menjalankan Program

### Prasyarat
- **Android Studio** (versi terbaru direkomendasikan, sudah termasuk JDK)
- Project sudah di-*sync* dengan Gradle tanpa error

### Langkah 1 — Buka Project
1. Buka Android Studio
2. `File` → `Open` → pilih folder project `TugasPraktikum2`
3. Tunggu proses indexing dan Gradle sync selesai (lihat progress bar di bagian bawah)

### Langkah 2 — Cek Dependency
Pastikan `shared/build.gradle.kts` sudah memiliki dependency berikut di dalam blok `kotlin { sourceSets { ... } }`:

```kotlin
commonMain.dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
}

commonTest.dependencies {
    implementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
}
```

Jika baru ditambahkan, klik **Sync Now** di notifikasi bagian atas editor, atau klik ikon gajah 🐘 **Sync Project with Gradle Files** di toolbar.

### Langkah 3 — Jalankan Program (Main.kt)
1. Di panel **Project** (kiri), navigasikan ke:
   `shared/src/commonMain/kotlin/com/example/tugaspraktikum2/newsfeed/Main.kt`
2. Buka file tersebut
3. Klik ikon ▶ (run) berwarna hijau di sebelah kiri baris `fun main() = runBlocking { ... }`
4. Pilih **Run 'MainKt'**
5. Lihat hasilnya di panel **Run** yang muncul di bagian bawah Android Studio

Program akan berjalan selama kurang lebih 13–14 detik, menampilkan:
- Berita baru yang muncul setiap 2 detik (sudah difilter kategori TEKNOLOGI)
- Perubahan `StateFlow` jumlah berita yang sudah dibaca
- Hasil pengambilan detail 3 berita secara paralel (async/await) di bagian akhir

### Langkah 4 — Jalankan Unit Test (Bonus)
1. Buka file `shared/src/commonTest/kotlin/com/example/tugaspraktikum2/newsfeed/NewsFeedManagerTest.kt`
2. Klik ikon ▶ di sebelah nama class `NewsFeedManagerTest`
3. Pilih **Run 'NewsFeedManagerTest'**
4. Tunggu hasilnya di panel **Run** — pastikan semua test bertanda centang hijau (pass)

Atau via terminal, dari root project:
```
./gradlew :shared:allTests
```

## Contoh Output

```
=== News Feed Simulator dimulai ===
Mengamati berita kategori TEKNOLOGI setiap 2 detik...

>> [StateFlow] Total berita sudah dibaca: 0
📰 Berita baru: [TEKNOLOGI] Kotlin 2.0 Resmi Dirilis dengan Performa Lebih Cepat
>> [StateFlow] Total berita sudah dibaca: 1
📰 Berita baru: [TEKNOLOGI] AI Generatif Mulai Diintegrasikan ke IDE
>> [StateFlow] Total berita sudah dibaca: 2
...

=== Simulasi selesai ===
```


## Konsep Kotlin yang Digunakan

- **Flow builder** (`flow { emit() }`) — cold stream, hanya berjalan saat di-`collect`.
- **Operators**: `filter`, `map`, `onEach`, `catch`, `launchIn`.
- **StateFlow**: state reaktif (`readCount`, `latestFeed`, `lastError`) yang selalu
  punya nilai terbaru dan bisa diamati banyak subscriber.
- **Coroutines**: `launch`, `async`/`await` untuk operasi paralel, `Dispatchers.IO`
  untuk simulasi network call, `SupervisorJob` agar satu error tidak membatalkan
  seluruh scope (structured concurrency yang aman).

## Troubleshooting Singkat

| Masalah | Solusi |
|---|---|
| `Unresolved reference` pada import | Pastikan package di tiap file sesuai lokasi foldernya (`com.example.tugaspraktikum2.newsfeed...`) |
| Gradle sync gagal | Cek koneksi internet, lalu klik Sync ulang; pastikan dependency di Langkah 2 sudah benar |
| `fun main()` tidak ada tombol ▶ | Pastikan file `Main.kt` ada di `commonMain`, bukan `commonTest`, dan Gradle sudah sync sukses |
| Test tidak jalan / tidak ketemu | Pastikan dependency `kotlinx-coroutines-test` dan `kotlin("test")` sudah ditambahkan di `commonTest.dependencies` |

