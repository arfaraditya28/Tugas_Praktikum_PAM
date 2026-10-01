package com.example.myprofileapp.data

data class ProfileUiState(
    val name: String = "Muhammad Arfa Raditya",
    val bio: String = "Mahasiswa Teknik Informatika ITERA Semester 5, yang sedang belajar mata kuliah Pengembangan Aplikasi Mobile",
    val email: String = "muhammad.124140015@student.itera.ac.id",
    val phone: String = "081278816220",
    val location: String = "Jl.Nangka Gang Beringin, Kedaton, Bandar Lampung",
    val isDarkMode: Boolean = false,
    val isEditing: Boolean = false
)
