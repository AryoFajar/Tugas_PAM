package com.example.tugaspraktikum1

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TugasPraktikum1",
    ) {
        App()
    }
}