package com.example.tugaspraktikum2

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "TugasPraktikum2",
    ) {
        App()
    }
}