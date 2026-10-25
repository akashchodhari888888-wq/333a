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
fun StoryScreen(viewModel: MainViewModel, navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) { viewModel.clearState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Story Saver", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            item {
                DownloaderBox(
                    placeholder = "Enter public username...",
                    onDownload = { viewModel.fetchMedia("https://www.instagram.com/$it/") },
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
            item { TutorialSection(TutorialData.tutorials.first { it.id == "story" }) }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewerScreen(viewModel: MainViewModel, navController: NavController) {
    val uiState by viewModel.uiState.collectAsState()
    LaunchedEffect(Unit) { viewModel.clearState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile Viewer", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            item {
                DownloaderBox(
                    placeholder = "Enter public username...",
                    onDownload = { viewModel.fetchMedia("https://www.instagram.com/$it/") },
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
            item { TutorialSection(TutorialData.tutorials.first { it.id == "viewer" }) }
        }
    }
}
