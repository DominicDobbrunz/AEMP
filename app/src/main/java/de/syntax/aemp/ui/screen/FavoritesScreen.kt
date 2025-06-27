package de.syntax.aemp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import de.syntax.aemp.ui.component.dental.DeviceCard
import de.syntax.aemp.ui.viewModel.FavoritesViewModel


@Composable
fun FavoritesScreen(
    navController: NavHostController,
    viewModel: FavoritesViewModel = viewModel(LocalContext.current as ViewModelStoreOwner)
) {
    val devices by viewModel.devices.collectAsState()
    val error by viewModel.error.collectAsState()

    Column() {
        Text(
            "Favoriten",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        Spacer(Modifier.height(16.dp))
        error?.let {
            Text(text = it, color = Color.Red, modifier = Modifier.padding(16.dp))
        }

        if (devices.isEmpty()) {
            Text(
                text = "Noch keine Favoriten hinzugefügt.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            LazyColumn {
                items(devices) { deviceUi ->
                    DeviceCard(
                        device = deviceUi.device,
                        isFavorite = true,
                        onFavClick = { viewModel.toggleFavorite(deviceUi) },
                        onClick = { /* Details */ }
                    )
                }
            }
        }
    }
}