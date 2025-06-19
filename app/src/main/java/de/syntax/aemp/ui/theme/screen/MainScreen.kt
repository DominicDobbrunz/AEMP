package de.syntax.aemp.ui.theme.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.syntax.aemp.di.BottomNavItem
import de.syntax.aemp.ui.theme.viewModel.UserViewModel

@Composable
fun MainScreen(navController: NavController, userViewModel: UserViewModel) {
    val navController = rememberNavController()
    val items = listOf(
        BottomNavItem.Dental,
        BottomNavItem.Favorites,
        BottomNavItem.Settings
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = BottomNavItem.Dental.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Dental.route) {
                DentalScreen(navController)
            }
            composable(BottomNavItem.Favorites.route) {
                FavoritesScreen()
            }
            composable(BottomNavItem.Settings.route) {
                SettingsScreen(navController, userViewModel)
            }
        }
    }
}