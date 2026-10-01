package com.example.tugaspraktikum2

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
