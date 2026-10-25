package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.ui.draw.clip
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.data.local.DownloadHistory
import com.example.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(viewModel: MainViewModel, navController: NavController) {
    val history by viewModel.history.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Download History", fontWeight = FontWeight.Bold) },
                actions = {
                    if (history.isNotEmpty()) {
                        IconButton(onClick = { viewModel.clearHistory() }) {
                            Icon(Icons.Default.DeleteSweep, contentDescription = "Clear All")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        if (history.isEmpty()) {
            EmptyState("No downloads yet", "Your history will appear here.")
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(history) { item ->
                    HistoryItem(item, onClick = { viewModel.fetchMedia(item.url) })
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
fun HistoryItem(item: DownloadHistory, onClick: () -> Unit) {
    ListItem(
        headlineContent = { Text(item.owner ?: "Unknown", fontWeight = FontWeight.Bold) },
        supportingContent = { Text(item.caption ?: item.url, maxLines = 1) },
        leadingContent = {
            AsyncImage(
                model = item.thumbnail,
                contentDescription = null,
                modifier = Modifier.size(56.dp).clip(androidx.compose.foundation.shape.RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        },
        trailingContent = {
            IconButton(onClick = onClick) {
                Icon(Icons.Default.Refresh, contentDescription = "Fetch Again")
            }
        },
        modifier = Modifier.clickable { onClick() }
    )
}

@Composable
fun EmptyState(title: String, description: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(Icons.Default.History, contentDescription = null, modifier = Modifier.size(64.dp), tint = Color.Gray.copy(alpha = 0.3f))
        Spacer(modifier = Modifier.height(16.dp))
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(description, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: MainViewModel, navController: NavController) {
    val themeMode by viewModel.themeMode.collectAsState()
    val language by viewModel.language.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Settings", fontWeight = FontWeight.Bold) }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text("Appearance", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            
            PreferenceItem("Theme", themeMode.replaceFirstChar { it.uppercase() }) {
                // Show theme picker dialog
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("General", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))

            PreferenceItem("Language", language.uppercase()) {
                // Show language picker dialog
            }

            PreferenceItem("Clear History", "") {
                viewModel.clearHistory()
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("About", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))

            PreferenceItem("Privacy Policy", "") { navController.navigate(com.example.ui.navigation.Privacy) }
            PreferenceItem("Terms of Service", "") { navController.navigate(com.example.ui.navigation.Terms) }
            PreferenceItem("About SaveGram", "") { navController.navigate(com.example.ui.navigation.About) }
        }
    }
}

@Composable
fun PreferenceItem(title: String, value: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        color = Color.Transparent
    ) {
        Row(
            modifier = Modifier.padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(value, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
                Icon(Icons.Default.ChevronRight, contentDescription = null, tint = Color.Gray)
            }
        }
    }
}
