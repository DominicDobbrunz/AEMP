package de.syntax.aemp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax.aemp.ui.theme.AEMPTheme
import de.syntax.aemp.ui.component.app.AppLauncher
import de.syntax.aemp.ui.component.app.Launch
import de.syntax.aemp.di.AppStart
import de.syntax.aemp.ui.viewModel.UserViewModel
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkTheme = isSystemInDarkTheme()

            AEMPTheme(darkTheme = isDarkTheme) {
                val navigationBottomBarColor = MaterialTheme.colorScheme.secondary

                val navigationBottomBarItemColors = NavigationBarItemDefaults.colors(
                    selectedIconColor = if (isDarkTheme) Color.Black else MaterialTheme.colorScheme.secondary,
                    unselectedIconColor = if (isDarkTheme) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = if (isDarkTheme) Color.Black else MaterialTheme.colorScheme.onSecondaryContainer,
                    unselectedTextColor = if (isDarkTheme) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
                AppLauncher(
                        duration = 3.seconds,
                        launchContent = { Launch() }
                    ) {
                        val userViewModel: UserViewModel = viewModel()
                        AppStart(
                            userViewModel = userViewModel,
                            bottomBarColor = navigationBottomBarColor,
                            bottomBarItemColors = navigationBottomBarItemColors
                        )
                }
            }
        }
    }
}


