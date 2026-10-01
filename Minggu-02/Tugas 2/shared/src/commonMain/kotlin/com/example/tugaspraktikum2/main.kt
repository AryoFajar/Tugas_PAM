package com.example.tugaspraktikum2.newsfeed

import com.example.tugaspraktikum2.newsfeed.manager.NewsFeedManager
import com.example.tugaspraktikum2.newsfeed.model.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
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
                manager.markAsRead()
            }
        }
    }

    manager.startObserving(category = Category.Technology, maxNews = 5)

    delay(12_000L)

    println("\n=== Mengambil detail berita 1, 2, 3 secara paralel ===")
    manager.fetchDetailsAsync(listOf(1, 2, 3)) { id, detail ->
        if (detail != null) {
            println("✅ Detail berita $id: ${detail.fullContent} (${detail.readTimeMinutes} menit baca)")
        } else {
            println("❌ Gagal mengambil detail berita $id")
        }
    }

    delay(1500L)

    manager.close()
    println("\n=== Simulasi selesai ===")
}