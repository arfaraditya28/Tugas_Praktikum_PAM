package com.example.newsfeedsimulator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newsfeedsimulator.ui.NewsDetailScreen
import com.example.newsfeedsimulator.ui.NewsFeedScreen
import com.example.newsfeedsimulator.ui.NewsTheme
import com.example.newsfeedsimulator.viewmodel.NewsViewModel

@Composable
@Preview
fun App() {
    NewsTheme(darkTheme = false) {
        val viewModel: NewsViewModel = viewModel { NewsViewModel() }
        val detail by viewModel.selectedDetail.collectAsState()
        val isLoading by viewModel.isDetailLoading.collectAsState()

        // Tampilkan detail jika sedang loading atau sudah ada datanya,
        // selain itu tampilkan feed utama.
        if (isLoading || detail != null) {
            NewsDetailScreen(viewModel)
        } else {
            NewsFeedScreen(viewModel)
        }
    }
}
