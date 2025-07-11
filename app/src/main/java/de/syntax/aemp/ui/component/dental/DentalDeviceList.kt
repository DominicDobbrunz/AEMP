package de.syntax.aemp.ui.component.dental

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import de.syntax.aemp.data.model.DeviceUi
import de.syntax.aemp.ui.alert.FavoriteAddedDialog
import de.syntax.aemp.ui.viewModel.DentalViewModel
import de.syntax.aemp.ui.viewModel.FavoritesViewModel


@Composable
fun DentalDeviceList(
    navController: NavController,
    devices: List<DeviceUi>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit,
    onFavoriteToggle: (DeviceUi) -> Unit,
    favoritesViewModel: FavoritesViewModel
) {
    var showDialog by remember { mutableStateOf(false) }

    Column {
        Spacer(Modifier.height(8.dp))
        // Geräteliste
        LazyColumn {
            items(devices) { deviceUi ->
                val isFavorite = favoritesViewModel.isFavorite(deviceUi.device)

                DeviceCard(
                    device = deviceUi.device,
                    isFavorite = isFavorite,
                    onFavClick = {
                        if (!isFavorite) showDialog = true
                        onFavoriteToggle(deviceUi)
                    }
                ) {
                    navController.navigate("detail/${deviceUi.device.id}")
                }
            }
            // Optional: Loading spinner oder End-of-List anzeigen
            item {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
        // Info Dialog
        if (showDialog) {
            FavoriteAddedDialog { showDialog = false }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DentalDeviceListPreview() {

}