package de.syntax.aemp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import de.syntax.aemp.R
import de.syntax.aemp.ui.component.dental.DentalDeviceList
import de.syntax.aemp.ui.component.dental.DentalFilterBar
import de.syntax.aemp.ui.component.profile.ProfileTextField
import de.syntax.aemp.ui.viewModel.DentalViewModel
import de.syntax.aemp.ui.viewModel.FavoritesViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.setValue
import de.syntax.aemp.ui.viewModel.SettingViewModel
import de.syntax.aemp.ui.viewModel.SettingViewModelFactory

@Composable
fun DentalScreen(navController: NavController) {
    val viewModel: DentalViewModel = viewModel(LocalContext.current as ViewModelStoreOwner)
    val favoritesViewModel: FavoritesViewModel = viewModel(LocalContext.current as ViewModelStoreOwner)
    val devices by viewModel.devices.collectAsState()
    val favorites by favoritesViewModel.devices.collectAsState()
    val error by viewModel.error.collectAsState()
    val (showSearch, setShowSearch) = remember { mutableStateOf(false) }
    val backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
    val context = LocalContext.current.applicationContext
    val settingViewModel: SettingViewModel = viewModel(factory = SettingViewModelFactory(context as android.app.Application))
    val notificationsEnabled by settingViewModel.notificationsEnabled.collectAsState()
    var showNotificationDialog by remember { mutableStateOf(false) }
    var checkedFirstStart by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (!checkedFirstStart) {
            // Prüfe, ob der User schon entschieden hat (z.B. in DataStore)
            // Hier: Wenn notificationsEnabled == null oder true (default), dann Dialog zeigen
            if (!notificationsEnabled) return@LaunchedEffect
            showNotificationDialog = true
            checkedFirstStart = true
        }
    }

    if (showNotificationDialog) {
        AlertDialog(
            onDismissRequest = { showNotificationDialog = false },
            title = { Text("Benachrichtigungen aktivieren?") },
            text = { Text("Es wurden neue Geräte hinzugefügt") },
            confirmButton = {
                TextButton(onClick = {
                    settingViewModel.setNotificationsEnabled(true)
                    showNotificationDialog = false
                }) {
                    Text("Aktivieren", color = MaterialTheme.colorScheme.onSecondary)
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    settingViewModel.setNotificationsEnabled(false)
                    showNotificationDialog = false
                }) {
                    Text("Später",color = MaterialTheme.colorScheme.onSecondary)
                }
            },
            containerColor = backgroundColor,
            shape = RoundedCornerShape(8.dp)
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Geräte",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp)
            )
            IconButton(onClick = { setShowSearch(!showSearch) }) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_lupe_48),
                    contentDescription = "Suche",
                    tint = Color.White
                )
            }
        }
        AnimatedVisibility(
            visible = showSearch,
            enter = slideInVertically(initialOffsetY = { -50 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -50 }) + fadeOut()
        ) {
            ProfileTextField(
                value = viewModel.searchText,
                label = "Geräte suchen",
                onValueChange = { viewModel.onSearchTextChange(it) }
            )
        }
        Spacer(Modifier.height(8.dp))
        DentalFilterBar(
            selectedCategory = viewModel.selectedCategory,
            onCategorySelected = { viewModel.onCategorySelected(it) }
        )
        if (error != null) {
            Text(text = error ?: "", color = Color.Red)
        }
        HorizontalDivider()
        DentalDeviceList(
            navController = navController,
            devices = devices,
            favorites = favorites,
            onFavoriteToggle = { device -> favoritesViewModel.toggleFavorite(device) }
        )
    }
}
