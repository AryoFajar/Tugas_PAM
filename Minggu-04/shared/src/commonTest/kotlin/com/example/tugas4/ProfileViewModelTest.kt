package com.example.tugas4

import com.example.tugas4.viewmodel.ProfileViewModel
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProfileViewModelTest {
    @Test
    fun savesTrimmedProfileAndClosesEditor() {
        val model = ProfileViewModel()
        model.startEditing()
        model.saveProfile("  Aryo  ", "  Bio baru  ")
        assertEquals("Aryo", model.uiState.value.name)
        assertEquals("Bio baru", model.uiState.value.bio)
        assertFalse(model.uiState.value.isEditing)
    }

    @Test
    fun rejectsBlankNameWithoutClosingEditor() {
        val model = ProfileViewModel()
        val original = model.uiState.value
        model.startEditing()
        model.saveProfile("   ", "Bio baru")
        assertEquals(original.name, model.uiState.value.name)
        assertEquals(original.bio, model.uiState.value.bio)
        assertTrue(model.uiState.value.isEditing)
    }

    @Test
    fun togglesAppearanceAndContactWithoutChangingProfile() {
        val model = ProfileViewModel()
        val original = model.uiState.value
        model.setDarkMode(false)
        model.toggleContact()
        assertFalse(model.uiState.value.isDarkMode)
        assertFalse(model.uiState.value.isContactVisible)
        model.toggleContact()
        assertTrue(model.uiState.value.isContactVisible)
        assertEquals(original.name, model.uiState.value.name)
    }
}
