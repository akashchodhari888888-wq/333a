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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(
    title: String,
    navController: NavController,
    content: @Composable ColumnScope.() -> Unit
) {
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            content()
        }
    }
}

@Composable
fun AboutScreen(navController: NavController) {
    InfoScreen("About SaveGram", navController) {
        Text("SaveGram is a powerful and easy-to-use Instagram downloader designed for personal use. We support downloading videos, photos, reels, and stories from public accounts anonymously.", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Our mission is to provide the best user experience for saving social media content for offline viewing. We prioritize speed, quality, and user privacy.", style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun ContactScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var success by remember { mutableStateOf(false) }

    InfoScreen("Contact Us", navController) {
        if (success) {
            Text("Message sent successfully! We will get back to you soon.", color = MaterialTheme.colorScheme.primary)
        } else {
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = message, onValueChange = { message = it }, label = { Text("Message") }, modifier = Modifier.fillMaxWidth(), minLines = 3)
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { success = true }, modifier = Modifier.fillMaxWidth()) {
                Text("Send Message")
            }
        }
    }
}

@Composable
fun PrivacyScreen(navController: NavController) {
    InfoScreen("Privacy Policy", navController) {
        LazyColumn {
            item {
                Text("Your privacy is important to us. SaveGram does not collect personal information. Any data downloaded is stored only on your device. We do not require login or access to your Instagram account.", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Disclaimer: This app is not affiliated with Instagram. All content belongs to its original creators. Only public content is supported. Downloads are for personal use only.", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun TermsScreen(navController: NavController) {
    InfoScreen("Terms of Service", navController) {
        LazyColumn {
            item {
                Text("By using SaveGram, you agree to respect copyright laws and the terms of service of Instagram. You must not use this app to download private or copyrighted content without permission.", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text("We are not responsible for any misuse of the downloaded content. Use this app at your own risk.", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun APIScreen(navController: NavController) {
    InfoScreen("Developer API", navController) {
        Text("SaveGram offers a simple REST API for developers. Access our high-speed downloading engine programmatically.", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Endpoint: POST /api/fetch", fontWeight = FontWeight.Bold)
        Text("Example Request:", style = MaterialTheme.typography.labelLarge)
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("curl -X POST -d '{\"url\": \"INSTAGRAM_URL\"}' https://api.savegram.app/api/fetch", modifier = Modifier.padding(8.dp), style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
fun FAQScreen(navController: NavController) {
    InfoScreen("FAQ", navController) {
        LazyColumn {
            item {
                FAQItem("Is it free?", "Yes, SaveGram is completely free to use.")
                FAQItem("Do I need to login?", "No, you don't need to login to your Instagram account.")
                FAQItem("Can I download private stories?", "No, we only support public accounts.")
            }
        }
    }
}

@Composable
fun FAQItem(question: String, answer: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(question, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
        Text(answer, style = MaterialTheme.typography.bodyMedium)
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp), thickness = 0.5.dp)
    }
}

@Composable
fun AppDownloadScreen(navController: NavController) {
    InfoScreen("Download App", navController) {
        Text("Get the best experience with our Android app.", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Handle APK Download link */ }, modifier = Modifier.fillMaxWidth()) {
            Text("Download SaveGram APK")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text("Installation Guide:", fontWeight = FontWeight.Bold)
        Text("1. Download the APK file.")
        Text("2. Enable 'Install from unknown sources' in settings.")
        Text("3. Open the file and tap 'Install'.")
    }
}
