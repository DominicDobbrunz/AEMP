package de.syntax.aemp.ui.screen

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import de.syntax.aemp.R
import de.syntax.aemp.ui.component.profile.SettingToggleItem
import de.syntax.aemp.ui.viewModel.SettingViewModel
import de.syntax.aemp.ui.viewModel.SettingViewModelFactory

@Composable
fun SettingsAdvancedScreen(
    navController: NavHostController
) {
    val context = LocalContext.current.applicationContext as Application
    val factory = remember { SettingViewModelFactory(context) }
    val viewModel: SettingViewModel = viewModel(factory = factory)

    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_doppelt_links_48),
                    contentDescription = "Zurück",
                    tint = Color.White
                )
            }
            Text(
                "Einstellungen",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        // Dark Mode Toggle
        SettingToggleItem(
            title = "Dark Mode",
            checked = isDarkMode,
            onCheckedChange = { viewModel.setDarkMode(it) }
        )

        // Benachrichtigungen Toggle
        SettingToggleItem(
            title = "Benachrichtigungen",
            checked = notificationsEnabled,
            onCheckedChange = { viewModel.setNotificationsEnabled(it) }
        )

        // Weitere Platzhalter
        SettingToggleItem(
            title = "Automatische Updates",
            checked = false,
            onCheckedChange = {}
        )
    }
}