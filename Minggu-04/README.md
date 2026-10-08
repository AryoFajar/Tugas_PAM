# Tugas 4 - Profile App dengan State Management dan MVVM
 
Tugas Praktikum Minggu 4 mata kuliah **Pengembangan Aplikasi Mobile (IF25-22017)**, Program Studi Teknik Informatika, Institut Teknologi Sumatera.
 
## Deskripsi
 
Aplikasi ini adalah pengembangan Profile App dari tugas minggu lalu. State aplikasi dipindahkan dari composable ke `ProfileViewModel` menggunakan `StateFlow`, sehingga UI hanya bertugas menampilkan state dan meneruskan aksi pengguna ke ViewModel.
 
## Fitur
 
1. **Implementasi MVVM**
   - `ProfileViewModel` dengan `StateFlow`.
   - `data class ProfileUiState` sebagai satu-satunya sumber state layar.
2. **Edit Profil**
   - Form untuk mengubah nama dan bio.
   - State hoisting pada TextField: `LabeledTextField` bersifat stateless.
   - Tombol **Simpan** memperbarui ViewModel, tombol **Batal** membuang perubahan.
   - Validasi: nama tidak boleh kosong, tombol Simpan nonaktif dan muncul pesan error.
3. **Dark Mode Toggle**
   - `Switch` untuk berpindah antara mode gelap dan terang.
   - Status mode disimpan di ViewModel, sehingga tetap terjaga saat data profil diubah.
   - Warna tema berganti dengan transisi halus (animasi 400 ms).
4. **Tampilkan/Sembunyikan Kontak** (dari tugas minggu lalu), kini juga dikelola oleh ViewModel.
## Screenshot
 
### Mode Gelap
 
<img width="1920" height="1051" alt="Tugas4 10_8_2026 10_58_00 AM" src="https://github.com/user-attachments/assets/6d575e5e-f29a-47ef-9c2d-66955d03c88a" />

### Mode Terang
 
<img width="1920" height="1051" alt="Tugas4 10_8_2026 10_58_06 AM" src="https://github.com/user-attachments/assets/94e4e5a6-299f-438b-9b28-fbe08c7ad79a" />

## Arsitektur
Aplikasi memakai pola **MVVM**. Data mengalir ke bawah sebagai state, sedangkan aksi pengguna mengalir ke atas sebagai event.
 
```
View (ui)  --- event --->  ViewModel  --- update --->  ProfileUiState (StateFlow)
   ^                                                          |
   +----------- state (collectAsState) <----------------------+
```
 
### Struktur Folder
 
```
shared/src/commonMain/kotlin/com/example/tugas4/
├── App.kt
├── data/
│   ├── ContactInfo.kt
│   └── ProfileUiState.kt
├── viewmodel/
│   └── ProfileViewModel.kt
└── ui/
    ├── EditProfileForm.kt
    ├── ProfileScreen.kt
    ├── ProfileTheme.kt
    └── components/
        ├── LabeledTextField.kt
        └── ProfileComponents.kt
```
 
### Tanggung Jawab Tiap File
| File | Peran |
|---|---|
| `data/ProfileUiState.kt` | Data class berisi nama, bio, daftar kontak, `isDarkMode`, `isEditing`, dan `isContactVisible` |
| `data/ContactInfo.kt` | Data class untuk satu item kontak |
| `viewmodel/ProfileViewModel.kt` | Menyimpan `StateFlow<ProfileUiState>` dan fungsi pengubah state (`setDarkMode`, `toggleContact`, `startEditing`, `cancelEditing`, `saveProfile`) |
| `ui/ProfileScreen.kt` | Layar profil yang stateless: menerima `uiState` dan callback |
| `ui/EditProfileForm.kt` | Form edit; menyimpan teks yang sedang diketik, lalu mengirimnya saat Simpan ditekan |
| `ui/ProfileTheme.kt` | Skema warna gelap dan terang dengan transisi `animateColorAsState` |
| `ui/components/LabeledTextField.kt` | TextField stateless yang reusable |
| `ui/components/ProfileComponents.kt` | Komponen UI: header, kartu, item kontak, toggle dark mode, dan tombol aksi |
| `App.kt` | Membuat ViewModel, mengumpulkan state dengan `collectAsState()`, dan menyambungkannya ke `ProfileScreen` |
 
## Teknologi
- Kotlin Multiplatform
- Compose Multiplatform 
- `lifecycle-viewmodel-compose` untuk ViewModel
- Kotlin `StateFlow` untuk state yang reaktif
  
## Cara Menjalankan
1. Clone repository ini.
2. Buka proyek di Android Studio dan tunggu sinkronisasi Gradle selesai.
3. Pilih konfigurasi run **desktop** (atau **Android**), lalu klik **Run**.
