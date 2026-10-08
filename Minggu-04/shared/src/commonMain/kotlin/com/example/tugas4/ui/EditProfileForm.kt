package com.example.tugas4.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugas4.ui.components.LabeledTextField
import com.example.tugas4.ui.components.ProfileCard

@Composable
fun EditProfileForm(
    initialName: String,
    initialBio: String,
    onSave: (name: String, bio: String) -> Unit,
    onCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    var name by rememberSaveable(initialName) { mutableStateOf(initialName) }
    var bio by rememberSaveable(initialBio) { mutableStateOf(initialBio) }
    val isNameValid = name.isNotBlank()

    ProfileCard(title = "Edit Profil", modifier = modifier) {
        LabeledTextField(
            label = "Nama",
            value = name,
            onValueChange = { name = it },
            isError = !isNameValid,
            errorText = if (!isNameValid) "Nama tidak boleh kosong" else null
        )
        Spacer(modifier = Modifier.height(12.dp))
        LabeledTextField(
            label = "Bio",
            value = bio,
            onValueChange = { bio = it },
            singleLine = false,
            minLines = 3
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text("Batal")
            }
            Button(
                onClick = { onSave(name, bio) },
                enabled = isNameValid,
                modifier = Modifier.weight(1f)
            ) {
                Text("Simpan")
            }
        }
    }
}
