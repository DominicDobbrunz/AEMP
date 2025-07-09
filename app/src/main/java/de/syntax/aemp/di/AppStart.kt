package de.syntax.aemp.di

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import de.syntax.aemp.ui.component.app.BackgroundImage
import de.syntax.aemp.ui.screen.AccountScreen
import de.syntax.aemp.ui.screen.DataProtectionScreen
import de.syntax.aemp.ui.screen.DentalScreen
import de.syntax.aemp.ui.screen.DeviceDetailScreen
import de.syntax.aemp.ui.screen.EditProfileScreen
import de.syntax.aemp.ui.screen.FavoritesScreen
import de.syntax.aemp.ui.screen.InformationScreen
import de.syntax.aemp.ui.screen.LoginScreen
import de.syntax.aemp.ui.screen.ProfileCheckScreen
import de.syntax.aemp.ui.screen.RegisterScreen
import de.syntax.aemp.ui.screen.SettingsAdvancedScreen
import de.syntax.aemp.ui.screen.SettingsScreen
import de.syntax.aemp.ui.screen.SupportScreen
import de.syntax.aemp.ui.viewModel.UserViewModel

@Composable
fun AppStart(
    userViewModel: UserViewModel = viewModel(),
    bottomBarColor: androidx.compose.ui.graphics.Color,
    bottomBarItemColors: NavigationBarItemColors
) {
    val navController = rememberNavController()
    val user by userViewModel.currentUser.collectAsState()

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route
    val showBottomBar = currentRoute in BottomNavItem.items.map { it.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(containerColor = bottomBarColor) {
                    BottomNavItem.items.forEach { item ->
                        NavigationBarItem(
                            selected = currentRoute == item.route,
                            onClick = {
                                if (currentRoute != item.route) {
                                    navController.navigate(item.route) {
                                        popUpTo(BottomNavItem.Dental.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(id = item.icon),
                                    contentDescription = item.label
                                )
                            },
                            label = { Text(item.label) },
                            colors = bottomBarItemColors
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize()) {
            BackgroundImage()

            NavHost(
                navController = navController,
                startDestination = if (user == null) "login" else "check_profile",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("login") {
                    LoginScreen(
                        onLoginWithGoogle = { /* TODO */ },
                        onLoginWithEmail = { email, password ->
                            userViewModel.loginWithEmail(email, password) { success ->
                                if (success) {
                                    navController.navigate("check_profile") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            }
                        },
                        onNavigateToRegister = {
                            navController.navigate("register")
                        }
                    )
                }
                composable("register") {
                    RegisterScreen(
                        navController = navController,
                        onRegisterSuccess = {
                            navController.navigate(BottomNavItem.Dental.route) {
                                popUpTo("register") { inclusive = true }
                            }
                        }
                    )
                }
                composable("check_profile") {
                    ProfileCheckScreen(
                        onProfileExists = {
                            navController.navigate(BottomNavItem.Dental.route) {
                                popUpTo("check_profile") { inclusive = true }
                            }
                        },
                        onProfileMissing = {
                            navController.navigate("complete_profile")
                        }
                    )
                }
                composable("detail/{id}") { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id")?.toIntOrNull()
                    if (id != null) {
                        DeviceDetailScreen(
                            id = id,
                            viewModel = viewModel(),
                            navController = navController
                        )
                    }
                }
                composable("editProfile") {
                    EditProfileScreen(onSave = {
                        navController.popBackStack()
                    })
                }
                composable("account") {
                    AccountScreen(navController)
                }
                composable("settings_advanced") {
                    SettingsAdvancedScreen(navController)
                }
                composable("information") {
                    InformationScreen(navController)
                }
                composable("support") {
                    SupportScreen(navController)
                }
                composable("privacy") {
                    DataProtectionScreen(navController)
                }
                composable(BottomNavItem.Dental.route) {
                    DentalScreen(navController)
                }
                composable(BottomNavItem.Favorites.route) {
                    FavoritesScreen(navController)
                }
                composable(BottomNavItem.Settings.route) {
                    SettingsScreen(navController)
                }
            }
        }
    }
}

/*


 */