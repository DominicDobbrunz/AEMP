package de.syntax.aemp.ui.theme

/* TODO für den DentalScreen
// Dann in der LazyColumn nur passende Geräte anzeigen:
val filteredDevices = if (selectedCategory == "Alle Geräte") {
    devices
} else {
    devices.filter { it.device.category == selectedCategory } // abhängig von deinem Datenmodell
}

TODO für den DentalDeviceList
// Geräte filtern
        val filteredDevices = if (searchText.isNotBlank()) {
            devices.filter {
                it.device.deviceName?.contains(searchText, ignoreCase = true) == true ||
                it.device.applicant?.contains(searchText, ignoreCase = true) == true
            }
        } else {
            devices
        }

        // Fehlermeldung
        if (error != null) {
            Text(error!!, color = Color.Red, modifier = Modifier.padding(8.dp))
        }

        // Geräte-Liste
        LazyColumn {
            items(filteredDevices) { deviceUi ->
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

val devices by viewModel.devices.collectAsState()

val filteredDevices = if (searchText.isNotBlank()) {
    devices.filter {
        it.device.deviceName?.contains(searchText, ignoreCase = true) == true ||
        it.device.applicant?.contains(searchText, ignoreCase = true) == true
    }
} else {
    devices
}
 */