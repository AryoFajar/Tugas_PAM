package com.example.tugaspraktikum2.newsfeed

import com.example.tugaspraktikum2.newsfeed.manager.NewsFeedManager
import com.example.tugaspraktikum2.newsfeed.model.Category
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class NewsFeedManagerTest {

    @Test
    fun `readCount bertambah setelah markAsRead dipanggil`() = runBlocking {
        val manager = NewsFeedManager()

        assertEquals(0, manager.readCount.value)

        manager.markAsRead()
        manager.markAsRead()

        assertEquals(2, manager.readCount.value)

        manager.close()
    }

    @Test
    fun `resetReadCount mengembalikan counter ke nol`() = runBlocking {
        val manager = NewsFeedManager()

        manager.markAsRead()
        manager.markAsRead()
        manager.resetReadCount()

        assertEquals(0, manager.readCount.value)

        manager.close()
    }

    @Test
    fun `startObserving hanya menghasilkan berita sesuai kategori filter`() = runBlocking {
        val manager = NewsFeedManager()

        manager.startObserving(category = Category.Sports, maxNews = 3)

        delay(6_500L)

        val feed = manager.latestFeed.value
        assertTrue(feed.all { it.categoryLabel.equals("Sports", ignoreCase = true) })

        manager.close()
    }

    @Test
    fun `fetchDetailsAsync mengembalikan hasil untuk semua id yang diminta`() = runBlocking {
        val manager = NewsFeedManager()
        val results = mutableMapOf<Int, Boolean>()

        manager.fetchDetailsAsync(listOf(10, 11, 12)) { id, detail ->
            results[id] = (detail != null)
        }

        delay(1_500L)

        assertEquals(3, results.size)
        manager.close()
    }
}
