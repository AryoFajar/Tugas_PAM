package com.example.tugaspraktikum2.newsfeed.manager

import com.example.tugaspraktikum2.newsfeed.model.Category
import com.example.tugaspraktikum2.newsfeed.model.NewsDetail
import com.example.tugaspraktikum2.newsfeed.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

val Category.displayName: String
    get() = when (this) {
        Category.Technology -> "TEKNOLOGI"
        Category.Sports -> "OLAHRAGA"
        Category.Policy -> "POLITIK"
        Category.Entertainment -> "HIBURAN"
        Category.Business -> "BISNIS"
    }

data class DisplayNews(
    val id: Int,
    val displayTitle: String,
    val categoryLabel: String
)

class NewsFeedManager(
    private val repository: NewsRepository = NewsRepository()
) {

    private val managerScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    private val _latestFeed = MutableStateFlow<List<DisplayNews>>(emptyList())
    val latestFeed: StateFlow<List<DisplayNews>> = _latestFeed.asStateFlow()

    private val _lastError = MutableStateFlow<String?>(null)
    val lastError: StateFlow<String?> = _lastError.asStateFlow()

    private var collectorJob: Job? = null

    fun startObserving(
        category: Category? = null,
        maxNews: Int = 20
    ) {
        collectorJob?.cancel()

        collectorJob = repository.newsFeed(categoryFilter = category, maxNews = maxNews)
            .filter { news ->
                category == null || news.category == category
            }
            .map { news ->
                DisplayNews(
                    id = news.id,
                    displayTitle = "[${news.category.displayName}] ${news.title}",
                    categoryLabel = news.category.name.lowercase()
                        .replaceFirstChar { it.uppercase() }
                )
            }
            .onEach { displayNews -> _latestFeed.value += displayNews }
            .catch { throwable ->
                _lastError.value = throwable.message ?: "Terjadi kesalahan tidak diketahui"
            }
            .launchIn(managerScope)
    }

    fun stopObserving() {
        collectorJob?.cancel()
    }

    fun markAsRead() {
        _readCount.value += 1
    }

    fun resetReadCount() {
        _readCount.value = 0
    }

    fun fetchDetailsAsync(
        newsIds: List<Int>,
        onResult: (Int, NewsDetail?) -> Unit
    ) {
        managerScope.launch {
            val deferredResults: List<Deferred<Pair<Int, NewsDetail?>>> = newsIds.map { id ->
                async {
                    try {
                        id to repository.fetchNewsDetail(id)
                    } catch (e: Exception) {
                        id to null
                    }
                }
            }

            deferredResults.forEach { deferred ->
                val (id, detail) = deferred.await()
                onResult(id, detail)
            }
        }
    }

    fun close() {
        managerScope.cancel()
    }
}

private fun CoroutineScope.cancel() {
    (coroutineContext[Job])?.cancel()
}