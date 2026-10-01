package com.example.tugaspraktikum1

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform