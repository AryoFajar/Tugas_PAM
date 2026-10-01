package com.example.tugaspraktikum2.newsfeed

import com.example.tugaspraktikum2.newsfeed.manager.NewsFeedManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {
    val manager = NewsFeedManager()

    println("=== News Feed Simulator dimulai ===")
    println("Mengamati berita kategori TEKNOLOGI setiap 2 detik...\n")

    launch {
        manager.readCount.collect { count ->
            println(">> [StateFlow] Total berita sudah dibaca: $count")
        }
    }

    launch {
        manager.lastError.collect { error ->
            if (error != null) println(">> [ERROR] $error")
        }
    }

    launch {
        manager.latestFeed.collect { list ->
            list.lastOrNull()?.let { newest ->
                println("📰 Berita baru: ${newest.displayTitle}")
                manager.markAsRead() // tandai otomatis sebagai "dibaca" untuk demo
            }
        }
    }
}
