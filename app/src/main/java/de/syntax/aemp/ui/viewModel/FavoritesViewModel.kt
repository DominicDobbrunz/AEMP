package de.syntax.aemp.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax.aemp.data.model.Device
import de.syntax.aemp.data.model.DeviceUi
import de.syntax.aemp.data.repository.DeviceRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    private val _favorites = MutableStateFlow<List<DeviceUi>>(emptyList())
    val devices: StateFlow<List<DeviceUi>> = _favorites.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun addFavorite(device: Device) {
        if (_favorites.value.none { it.device.kNumber == device.kNumber }) {
            _favorites.value = _favorites.value + DeviceUi(device, isFavorited = true)
        }
    }

    fun removeFavorite(device: Device) {
        _favorites.value = _favorites.value.filterNot { it.device.kNumber == device.kNumber }
    }

    fun toggleFavorite(deviceUi: DeviceUi) {
        if (deviceUi.isFavorited) {
            removeFavorite(deviceUi.device)
        } else {
            addFavorite(deviceUi.device)
        }
    }

    fun isFavorite(device: Device): Boolean {
        return _favorites.value.any { it.device.kNumber == device.kNumber }
    }
}