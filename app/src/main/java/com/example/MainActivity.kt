package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.data.datastore.PreferencesManager
import com.example.data.local.AppDatabase
import com.example.data.remote.NetworkModule
import com.example.data.repository.SaveGramRepository
import com.example.ui.components.SaveGramBottomBar
import com.example.ui.navigation.*
import com.example.ui.screens.*
import com.example.ui.theme.SaveGramTheme
import com.example.ui.viewmodel.MainViewModel
import com.example.ui.viewmodel.MainViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        val database = AppDatabase.getDatabase(this)
        val repository = SaveGramRepository(NetworkModule.api, database.historyDao())
        val preferencesManager = PreferencesManager(this)
        val factory = MainViewModelFactory(repository, preferencesManager)

        setContent {
            val viewModel: MainViewModel = viewModel(factory = factory)
            
            // Handle Deep Link
            LaunchedEffect(intent?.data) {
                intent?.data?.let { uri ->
                    if (uri.path?.startsWith("/d/") == true) {
                        val instagramUrl = uri.toString().substringAfter("/d/")
                        if (instagramUrl.isNotEmpty()) {
                            viewModel.fetchMedia(instagramUrl)
                        }
                    }
                }
            }
            val themeMode by viewModel.themeMode.collectAsState()
            
            val isDarkTheme = when (themeMode) {
                "dark" -> true
                "light" -> false
                else -> androidx.compose.foundation.isSystemInDarkTheme()
            }

            SaveGramTheme(darkTheme = isDarkTheme) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
                    bottomBar = {
                        SaveGramBottomBar(navController, currentDestination)
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Home,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<Home> { HomeScreen(viewModel, navController) }
                        composable<Video> { VideoScreen(viewModel, navController) }
                        composable<Photo> { PhotoScreen(viewModel, navController) }
                        composable<Reels> { ReelsScreen(viewModel, navController) }
                        composable<Story> { StoryScreen(viewModel, navController) }
                        composable<IGTV> { IGTVScreen(viewModel, navController) }
                        composable<Carousel> { CarouselScreen(viewModel, navController) }
                        composable<Viewer> { ViewerScreen(viewModel, navController) }
                        composable<MP4> { MP4Screen(viewModel, navController) }
                        composable<History> { HistoryScreen(viewModel, navController) }
                        composable<Settings> { SettingsScreen(viewModel, navController) }
                        composable<About> { AboutScreen(navController) }
                        composable<Contact> { ContactScreen(navController) }
                        composable<API> { APIScreen(navController) }
                        composable<FAQ> { FAQScreen(navController) }
                        composable<Privacy> { PrivacyScreen(navController) }
                        composable<Terms> { TermsScreen(navController) }
                        composable<AppDownload> { AppDownloadScreen(navController) }
                        composable<DirectDownload> { backStackEntry ->
                            // Handle /d/* equivalent
                        }
                    }
                }
            }
        }
    }
}
