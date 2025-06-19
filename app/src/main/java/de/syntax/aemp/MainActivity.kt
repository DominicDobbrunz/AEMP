package de.syntax.aemp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import de.syntax.aemp.ui.theme.AEMPTheme
import de.syntax.aemp.ui.theme.composable.AppLauncher
import de.syntax.aemp.ui.theme.composable.Launch
import de.syntax.aemp.di.RootApp
import de.syntax.aemp.ui.theme.viewModel.UserViewModel
import kotlin.time.Duration.Companion.seconds

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AEMPTheme {
                    AppLauncher(
                        duration = 3.seconds,
                        launchContent = { Launch() }
                    ) {
                        val userViewModel: UserViewModel = viewModel()
                        RootApp(userViewModel)
                    }
            }
        }
    }
}

