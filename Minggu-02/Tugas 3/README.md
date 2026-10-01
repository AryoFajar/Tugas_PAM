# My Profile App — Tugas Praktikum Minggu 3

Aplikasi profil sederhana yang dibuat dengan **Compose Multiplatform** sebagai tugas praktikum mata kuliah **Pengembangan Aplikasi Mobile (IF25-22017)**, Program Studi Teknik Informatika, Institut Teknologi Sumatera.

## Identitas

| | |
|---|---|
| **Nama** | Aryo Fajar Pratomo |
| **NIM** | 124140012 |
| **Email** | aryo.124140012@student.itera.ac.id |

## Screenshot

### Desktop
[Uploading Cuplikan layar 2026-09-30 205250.png]()
[Tampilan Desktop]

## Fitur

- Header dengan foto profil berbentuk lingkaran, nama, dan NIM
- Bio singkat
- Informasi kontak: Email, Phone, Location
- Tombol untuk menampilkan atau menyembunyikan kontak dengan animasi (`AnimatedVisibility`)
- Tema gelap dengan warna merah dan hitam yang disesuaikan dengan foto profil

## Composable Functions

| Nama | Fungsi |
|---|---|
| `ProfileScreen` | Halaman utama yang menyusun seluruh komponen dengan `Column` |
| `ProfileHeader` | Header berisi foto profil, nama, dan status (`Box` + `Column`) |
| `ProfileCard` | Kartu pembungkus dengan judul section, isinya berupa slot yang bisa dipakai ulang |
| `InfoItem` | Satu baris informasi berisi ikon, label, dan nilai (`Row`) |
| `ProfileActions` | Baris tombol aksi (`Row` dengan `weight`) |

## Komponen yang Digunakan

- **Layout:** `Column`, `Row`, `Box`
- **UI Components:** `Text`, `Button`, `OutlinedButton`, `Image`, `Icon`, `Card`
- **Modifier:** `fillMaxWidth`, `fillMaxSize`, `padding`, `size`, `height`, `width`, `weight`, `background`, `border`, `clip`, `verticalScroll`
- **Lainnya:** `AnimatedVisibility`, `remember` + `mutableStateOf`, `MaterialTheme` dengan `darkColorScheme`



## Cara Menjalankan

Lewat Android Studio:
 
1. Pilih konfigurasi **desktopApp** pada dropdown run di bagian atas.
2. Klik tombol **Run** (▶) atau tekan `Shift + F10`.
Lewat terminal:
 
```bash
./gradlew :desktopApp:run
```
 
Pengguna Windows dapat memakai `gradlew.bat :desktopApp:run`.

## Teknologi

- Kotlin
- Compose Multiplatform
