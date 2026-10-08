package com.example.tugas4.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tugas4.data.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun setDarkMode(enabled: Boolean) {
        _uiState.update { it.copy(isDarkMode = enabled) }
    }

    fun toggleContact() {
        _uiState.update { it.copy(isContactVisible = !it.isContactVisible) }
    }

    fun startEditing() {
        _uiState.update { it.copy(isEditing = true) }
    }

    fun cancelEditing() {
        _uiState.update { it.copy(isEditing = false) }
    }

    fun saveProfile(name: String, bio: String) {
        val cleanName = name.trim()
        if (cleanName.isEmpty()) return

        _uiState.update {
            it.copy(
                name = cleanName,
                bio = bio.trim(),
                isEditing = false
            )
        }
    }
}