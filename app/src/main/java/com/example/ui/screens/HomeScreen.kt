package com.example.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.R
import com.example.data.TutorialData
import com.example.ui.components.*
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.UiState

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    val uiState by viewModel.uiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SaveGramTopBar(onMenuClick = { /* Open drawer or menu */ })
        }

        item {
            HeroSection()
        }

        item {
            DownloaderBox(
                placeholder = "Paste Instagram link here...",
                onDownload = { viewModel.fetchMedia(it) },
                isLoading = uiState is UiState.Loading
            )
        }

        item {
            when (uiState) {
                is UiState.Success -> {
                    ResultCard(
                        data = (uiState as UiState.Success).data,
                        onDownloadItem = { /* Handle download logic */ },
                        onShare = { /* Handle share logic */ }
                    )
                }
                is UiState.Error -> {
                    ErrorMessage((uiState as UiState.Error).message)
                }
                else -> {}
            }
        }

        item {
            QuickToolsGrid(navController)
        }

        item {
            TutorialSection(TutorialData.tutorials.first { it.id == "home" })
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun HeroSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_hero_banner),
            contentDescription = "Hero banner",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun QuickToolsGrid(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Our Tools", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        
        val tools = listOf(
            Triple("Video", Icons.Default.PlayCircle, com.example.ui.navigation.Video),
            Triple("Photo", Icons.Default.Image, com.example.ui.navigation.Photo),
            Triple("Reels", Icons.Default.Movie, com.example.ui.navigation.Reels),
            Triple("Story", Icons.Default.AccountCircle, com.example.ui.navigation.Story),
            Triple("IGTV", Icons.Default.Tv, com.example.ui.navigation.IGTV),
            Triple("Carousel", Icons.Default.ViewCarousel, com.example.ui.navigation.Carousel)
        )

        tools.chunked(2).forEach { rowTools ->
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                rowTools.forEach { tool ->
                    ToolCard(
                        title = tool.first,
                        icon = tool.second,
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate(tool.third) }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ToolCard(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = modifier,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(title, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
fun ErrorMessage(message: String) {
    Surface(
        color = MaterialTheme.colorScheme.errorContainer,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp),
        modifier = Modifier.padding(16.dp).fillMaxWidth()
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Icon(Icons.Default.Error, contentDescription = null, tint = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = message, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onErrorContainer)
        }
    }
}
