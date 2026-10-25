package com.example.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data.remote.DownloadItem
import com.example.data.remote.FetchResponse

@Composable
fun ResultCard(
    data: FetchResponse,
    onDownloadItem: (DownloadItem) -> Unit,
    onShare: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = data.thumbnail,
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = data.owner ?: "Unknown User",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Badge(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                        Text(data.type.uppercase(), modifier = Modifier.padding(horizontal = 4.dp))
                    }
                }
            }

            if (!data.caption.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = data.caption,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), thickness = 0.5.dp)

            data.items.forEach { item ->
                DownloadOptionItem(item, onDownloadItem)
                Spacer(modifier = Modifier.height(8.dp))
            }

            Button(
                onClick = { onShare(data.items.firstOrNull()?.downloadUrl ?: "") },
                modifier = Modifier.fillMaxWidth(),
                variant = ButtonVariant.OUTLINED
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Share Link")
            }
        }
    }
}

@Composable
private fun DownloadOptionItem(
    item: DownloadItem,
    onDownloadItem: (DownloadItem) -> Unit
) {
    Surface(
        onClick = { onDownloadItem(item) },
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = if (item.type == "video") "Download Video" else "Download Photo",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${item.qualityLabel ?: "Standard"} • ${item.sizeLabel ?: "Unknown size"}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Icon(Icons.Default.Download, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: ButtonVariant = ButtonVariant.FILLED,
    content: @Composable RowScope.() -> Unit
) {
    when (variant) {
        ButtonVariant.FILLED -> Button(onClick = onClick, modifier = modifier, content = content)
        ButtonVariant.OUTLINED -> OutlinedButton(onClick = onClick, modifier = modifier, content = content)
    }
}

enum class ButtonVariant { FILLED, OUTLINED }
