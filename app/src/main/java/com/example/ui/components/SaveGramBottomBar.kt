package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.ui.navigation.*

@Composable
fun SaveGramBottomBar(
    navController: NavController,
    currentDestination: NavDestination?
) {
    val items = listOf(
        Triple(Home, "Home", Icons.Default.Home),
        Triple(Reels, "Reels", Icons.Default.Movie),
        Triple(Story, "Story", Icons.Default.Person),
        Triple(History, "History", Icons.Default.History),
        Triple(Settings, "Settings", Icons.Default.Settings)
    )

    // Only show bottom bar for main top-level screens
    val topLevelRoutes = listOf(Home::class, Reels::class, Story::class, History::class, Settings::class)
    val showBottomBar = topLevelRoutes.any { route -> 
        currentDestination?.hierarchy?.any { it.hasRoute(route) } == true 
    }

    if (showBottomBar) {
        NavigationBar {
            items.forEach { (route, label, icon) ->
                val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(route::class) } == true
                NavigationBarItem(
                    icon = { Icon(icon, contentDescription = label) },
                    label = { Text(label) },
                    selected = isSelected,
                    onClick = {
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
