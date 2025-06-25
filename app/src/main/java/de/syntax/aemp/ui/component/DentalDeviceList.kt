package de.syntax.aemp.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import de.syntax.aemp.data.model.DeviceUi
import de.syntax.aemp.ui.viewModel.DentalViewModel
import de.syntax.aemp.ui.viewModel.FavoritesViewModel


@Composable
fun DentalDeviceList(
    navController: NavController,
    viewModel: DentalViewModel = viewModel(),
    favoritesViewModel: FavoritesViewModel = viewModel(LocalContext.current as ViewModelStoreOwner) // <-- shared ViewModel
) {
    val devices by viewModel.devices.collectAsState()
    val error by viewModel.error.collectAsState()

    var searchText by remember { mutableStateOf("") }

    Column {
        ProfileTextField(value = searchText, label = "Geräte suche") { searchText = it }

        if (error != null) {
            Text(error!!, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        LazyColumn {
            items(devices) { deviceUi ->
                val isFavorite = favoritesViewModel.isFavorite(deviceUi.device)

                DeviceCard(
                    device = deviceUi.device,
                    isFavorite = isFavorite,
                    onFavClick = {
                        favoritesViewModel.toggleFavorite(DeviceUi(deviceUi.device, isFavorite))
                    }
                ) {
                    navController.navigate("detail/${deviceUi.device.kNumber}")
                }
            }

            item {
                // Lazy loading bei Scroll-Ende
                LaunchedEffect(devices.size) {
                    viewModel.loadDevices()
                }
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DentalDeviceListPreview() {

}