package com.example.tugas4

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tugas4.ui.ProfileScreen
import com.example.tugas4.ui.ProfileTheme
import com.example.tugas4.viewmodel.ProfileViewModel
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val viewModel = viewModel { ProfileViewModel() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ProfileTheme(darkTheme = uiState.isDarkMode) {
        ProfileScreen(
            uiState = uiState,
            onToggleDarkMode = viewModel::setDarkMode,
            onToggleContact = viewModel::toggleContact,
            onStartEdit = viewModel::startEditing,
            onCancelEdit = viewModel::cancelEditing,
            onSaveProfile = viewModel::saveProfile
        )
    }
}
