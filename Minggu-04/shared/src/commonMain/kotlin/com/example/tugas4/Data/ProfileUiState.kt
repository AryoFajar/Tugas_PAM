package com.example.tugas4.data

data class ProfileUiState(
    val name: String = "Aryo Fajar Pratomo",
    val studentInfo: String = "124140012 | Teknik Informatika - ITERA",
    val bio: String = "Halo! Saya Aryo, mahasiswa Teknik Informatika Institut Teknologi Sumatera",
    val contacts: List<ContactInfo> = listOf(
        ContactInfo("Email", "aryo.124140012@student.itera.ac.id"),
        ContactInfo("Phone", "+62 895 3221 07632"),
        ContactInfo("Location", "Bandar Lampung, Lampung, Indonesia")
    ),
    val isDarkMode: Boolean = true,
    val isEditing: Boolean = false,
    val isContactVisible: Boolean = true
)
