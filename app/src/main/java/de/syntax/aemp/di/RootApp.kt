package de.syntax.aemp.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.syntax.aemp.ui.theme.screen.CompleteProfileScreen
import de.syntax.aemp.ui.theme.screen.DeviceDetailScreen
import de.syntax.aemp.ui.theme.screen.FavoritesScreen
import de.syntax.aemp.ui.theme.screen.LoginScreen
import de.syntax.aemp.ui.theme.screen.MainScreen
import de.syntax.aemp.ui.theme.screen.ProfileCheckScreen
import de.syntax.aemp.ui.theme.screen.RegisterScreen
import de.syntax.aemp.ui.theme.viewModel.UserViewModel


@Composable
fun RootApp(userViewModel: UserViewModel = viewModel()) {
    val navController = rememberNavController()
    val user by userViewModel.currentUser.collectAsState()

    NavHost(
        navController = navController,
        startDestination = if (user == null) "login" else "check_profile"
    ) {
        composable("login") {
            LoginScreen(
                onLoginWithGoogle = {
                    // TODO: Übergib Activity/Launcher falls notwendig
                },
                onLoginWithEmail = { email, password ->
                    userViewModel.loginWithEmail(email, password) { success ->
                        if (success) {
                            navController.navigate("check_profile") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }
                },
                onNavigateToRegister = { navController.navigate("register") }
            )
        }
        composable("register") {
            RegisterScreen(
                navController = navController,
                onRegisterSuccess = { navController.navigate("login") }
            )
        }
        composable("check_profile") {
            ProfileCheckScreen(
                onProfileExists = { navController.navigate("device_list") },
                onProfileMissing = { navController.navigate("complete_profile") }
            )
        }
        composable("complete_profile") {
            CompleteProfileScreen {
                navController.navigate("device_list")
            }
        }
        composable("device_list") {
            MainScreen(
                userViewModel,
                userViewModel = UserViewModel()
            )
        }
        composable("favorite") {
            FavoritesScreen()
        }
        composable("detail/{kNumber}") { backStackEntry ->
            val kNumber = backStackEntry.arguments?.getString("kNumber")
            DeviceDetailScreen(kNumber, viewModel())
        }
    }
}
