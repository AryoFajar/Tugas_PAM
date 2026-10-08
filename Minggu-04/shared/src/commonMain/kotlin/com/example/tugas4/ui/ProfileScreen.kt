package com.example.tugas4.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tugas4.data.ProfileUiState
import com.example.tugas4.ui.components.DarkModeToggle
import com.example.tugas4.ui.components.InfoItem
import com.example.tugas4.ui.components.ProfileActions
import com.example.tugas4.ui.components.ProfileCard
import com.example.tugas4.ui.components.ProfileHeader

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onToggleDarkMode: (Boolean) -> Unit,
    onToggleContact: () -> Unit,
    onStartEdit: () -> Unit,
    onCancelEdit: () -> Unit,
    onSaveProfile: (name: String, bio: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .imePadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileHeader(
            name = uiState.name,
            title = uiState.studentInfo
        )

        DarkModeToggle(
            isDarkMode = uiState.isDarkMode,
            onCheckedChange = onToggleDarkMode
        )

        if (uiState.isEditing) {
            EditProfileForm(
                initialName = uiState.name,
                initialBio = uiState.bio,
                onSave = onSaveProfile,
                onCancel = onCancelEdit
            )
        } else {
            ProfileCard(title = "Tentang Saya") {
                Text(
                    text = uiState.bio,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 14.sp
                )
            }
        }

        ProfileActions(
            isEditing = uiState.isEditing,
            isContactVisible = uiState.isContactVisible,
            onEditClick = onStartEdit,
            onToggleContact = onToggleContact
        )

        AnimatedVisibility(visible = uiState.isContactVisible) {
            ProfileCard(title = "Informasi Kontak") {
                uiState.contacts.forEach { contact ->
                    InfoItem(label = contact.label, value = contact.value)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}
