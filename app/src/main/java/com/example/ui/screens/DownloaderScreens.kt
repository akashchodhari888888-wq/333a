package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.data.TutorialData
import com.example.ui.components.*
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedDownloaderScreen(
    title: String,
    placeholder: String,
    tutorialId: String,
    viewModel: MainViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) { viewModel.clearState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                DownloaderBox(
                    placeholder = placeholder,
                    onDownload = { viewModel.fetchMedia(it) },
                    isLoading = uiState is UiState.Loading
                )
            }

            item {
                when (uiState) {
                    is UiState.Success -> {
                        ResultCard(
                            data = (uiState as UiState.Success).data,
                            onDownloadItem = { /* Handle download */ },
                            onShare = { /* Handle share */ }
                        )
                    }
                    is UiState.Error -> {
                        ErrorMessage((uiState as UiState.Error).message)
                    }
                    else -> {}
                }
            }

            item {
                TutorialSection(TutorialData.tutorials.first { it.id == tutorialId || it.id == "home" })
            }
            
            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun VideoScreen(viewModel: MainViewModel, navController: NavController) = 
    SharedDownloaderScreen("Video Downloader", "Paste video link...", "home", viewModel, navController)

@Composable
fun PhotoScreen(viewModel: MainViewModel, navController: NavController) = 
    SharedDownloaderScreen("Photo Downloader", "Paste photo link...", "home", viewModel, navController)

@Composable
fun ReelsScreen(viewModel: MainViewModel, navController: NavController) = 
    SharedDownloaderScreen("Reels Downloader", "Paste reels link...", "home", viewModel, navController)

@Composable
fun IGTVScreen(viewModel: MainViewModel, navController: NavController) = 
    SharedDownloaderScreen("IGTV Downloader", "Paste IGTV link...", "home", viewModel, navController)

@Composable
fun CarouselScreen(viewModel: MainViewModel, navController: NavController) = 
    SharedDownloaderScreen("Carousel Downloader", "Paste carousel link...", "home", viewModel, navController)

@Composable
fun MP4Screen(viewModel: MainViewModel, navController: NavController) = 
    SharedDownloaderScreen("Instagram to MP4", "Paste video link for MP4...", "home", viewModel, navController)
