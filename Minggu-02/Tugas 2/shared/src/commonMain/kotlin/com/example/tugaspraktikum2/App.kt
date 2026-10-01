package com.example.tugaspraktikum2

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tugaspraktikum2.newsfeed.manager.NewsFeedManager
import com.example.tugaspraktikum2.newsfeed.model.Category

@Composable
fun App() {
    val manager = remember { NewsFeedManager() }
    val readCount by manager.readCount.collectAsState()
    val latestFeed by manager.latestFeed.collectAsState()
    val lastError by manager.lastError.collectAsState()

    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    var isObserving by remember { mutableStateOf(false) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "News Feed Simulator",
                    style = MaterialTheme.typography.headlineMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            if (isObserving) {
                                manager.stopObserving()
                                isObserving = false
                            } else {
                                manager.startObserving(selectedCategory)
                                isObserving = true
                            }
                        }
                    ) {
                        Text(if (isObserving) "Stop Observing" else "Start Observing")
                    }

                    Button(
                        onClick = { manager.resetReadCount() }
                    ) {
                        Text("Reset Read: $readCount")
                    }
                }

                if (lastError != null) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Error: $lastError",
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.padding(12.dp)
                        )
                    }
                }

                Text(
                    text = "Total Berita Dibaca: $readCount",
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = "Daftar Berita:",
                    style = MaterialTheme.typography.titleMedium
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(latestFeed) { news ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                manager.markAsRead()
                            }
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = news.displayTitle,
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    text = "Kategori: ${news.categoryLabel}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
