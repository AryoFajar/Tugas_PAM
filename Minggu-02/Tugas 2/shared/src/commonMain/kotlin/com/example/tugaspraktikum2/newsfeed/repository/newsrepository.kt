package com.example.tugaspraktikum2.newsfeed.repository

import com.example.tugaspraktikum2.newsfeed.model.Category
import com.example.tugaspraktikum2.newsfeed.model.News
import com.example.tugaspraktikum2.newsfeed.model.NewsDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlin.random.Random


class NewsRepository {

    private val sampleTitles = mapOf(
        Category.Technology to listOf(
            "Kotlin 2.0 Resmi Dirilis dengan Performa Lebih Cepat",
            "AI Generatif Mulai Diintegrasikan ke IDE"
        ),
        Category.Sports to listOf(
            "Timnas Menang Telak di Laga Persahabatan",
            "Rekor Baru Dipecahkan di Ajang Lari Maraton"
        ),
        Category.Policy to listOf(
            "DPR Sahkan Undang-Undang Baru",
            "Pemilu Daerah Digelar Serentak Tahun Depan"
        ),
        Category.Entertainment to listOf(
            "Film Lokal Tembus Box Office Internasional",
            "Konser Musik Tahunan Pecahkan Rekor Penonton"
        ),
        Category.Business to listOf(
            "Rupiah Menguat Terhadap Dolar AS",
            "UMKM Digital Tumbuh Pesat di Kuartal Ini"
        )
    )

    fun newsFeed(categoryFilter: Category? = null, maxNews: Int = 20): Flow<News> = flow {
        var idCounter = 1
        var emittedCount = 0
        while (emittedCount < maxNews) {
            delay(2000L)

            val category = categoryFilter ?: Category.entries.random()
            val titles = sampleTitles.getValue(category)
            val title = titles[(idCounter - 1) % titles.size]

            emit(
                News(
                    id = idCounter,
                    title = title,
                    category = category,
                    timestamp = idCounter.toLong() * 2000L
                )
            )
            idCounter++
            emittedCount++
        }
    }

    suspend fun fetchNewsDetail(newsId: Int): NewsDetail = withContext(Dispatchers.IO) {
        delay(800L)

        val readTime = when (newsId) {
            1 -> 4
            2 -> 2
            3 -> 5
            else -> Random.nextInt(1, 6)
        }

        NewsDetail(
            id = newsId,
            fullContent = "Ini adalah isi lengkap berita dengan id $newsId. " +
                    "Konten ini disimulasikan untuk keperluan latihan Coroutines & Flow.",
            author = "Redaksi ITERA News",
            readTimeMinutes = readTime
        )
    }
}
