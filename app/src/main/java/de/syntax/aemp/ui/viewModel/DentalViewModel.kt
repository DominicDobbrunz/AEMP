package de.syntax.aemp.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax.aemp.data.model.DeviceUi
import de.syntax.aemp.data.repository.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DentalViewModel : ViewModel() {
    private val repo = DeviceRepository()

    private val _allDevices = MutableStateFlow<List<DeviceUi>>(emptyList())
    private val _devices = MutableStateFlow<List<DeviceUi>>(emptyList())
    val devices: StateFlow<List<DeviceUi>> = _devices

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    private var favoritesViewModel: FavoritesViewModel? = null

    var searchText by mutableStateOf("")
        private set

    var selectedCategory by mutableStateOf("Alle Geräte")
        private set

    init {
        loadDevices()
    }

    private fun loadDevices() {
        viewModelScope.launch {
            try {
                val list = repo.fetchDevices()
                val favoriteDevices = repo.getFavoriteDevices(list)
                _allDevices.value = favoriteDevices
                applyFilters()
            } catch (e: Exception) {
                _error.value = "Fehler: ${e.message}"
            }
        }
    }

    fun setFavoritesViewModel(vm: FavoritesViewModel) {
        favoritesViewModel = vm
    }

    fun toggleFavorite(deviceUi: DeviceUi) {
        repo.toggleFavorite(deviceUi.device)
        _devices.value = _devices.value.map {
            it.copy(isFavorited = repo.isFavorite(it.device))
        }
        if (deviceUi.isFavorited) {
            favoritesViewModel?.removeFavorite(deviceUi.device)
        } else {
            favoritesViewModel?.addFavorite(deviceUi.device)
        }
        favoritesViewModel?.loadFavorites()
    }

    fun onSearchTextChange(text: String) {
        searchText = text
        applyFilters()
    }

    fun onCategorySelected(category: String) {
        selectedCategory = category
        applyFilters()
    }

    private fun applyFilters() {
        val filtered = _allDevices.value.filter { device ->
            val matchesSearch = device.device.name.contains(searchText, ignoreCase = true)
            val matchesCategory = selectedCategory == "Alle Geräte" ||
                    device.device.category.equals(selectedCategory, ignoreCase = true)

            matchesSearch && matchesCategory
        }
        _devices.value = filtered
    }
}
