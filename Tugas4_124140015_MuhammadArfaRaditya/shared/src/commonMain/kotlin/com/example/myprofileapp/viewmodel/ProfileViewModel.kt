package com.example.myprofileapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myprofileapp.data.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel pengelola UI state Profile App.
 *
 * Pola: UI -> event -> ViewModel -> update StateFlow -> recomposition.
 */
class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    /** Dipanggil tombol Save di EditProfileScreen. */
    fun saveProfile(name: String, bio: String) {
        _uiState.update { current ->
            current.copy(
                name = name.trim().ifBlank { current.name },
                bio = bio.trim().ifBlank { current.bio },
                isEditing = false
            )
        }
    }

    fun setEditing(isEditing: Boolean) {
        _uiState.update { it.copy(isEditing = isEditing) }
    }

    fun setDarkMode(enabled: Boolean) {
        _uiState.update { it.copy(isDarkMode = enabled) }
    }

    fun toggleDarkMode() {
        _uiState.update { it.copy(isDarkMode = !it.isDarkMode) }
    }
}
