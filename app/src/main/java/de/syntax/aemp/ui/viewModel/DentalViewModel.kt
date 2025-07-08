package de.syntax.aemp.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.syntax.aemp.data.model.DeviceUi
import de.syntax.aemp.data.repository.DeviceRepository
import kotlinx.coroutines.tasks.await

class DentalViewModel : ViewModel() {
    private val repo = DeviceRepository()

    private val _allDevices = MutableStateFlow<List<DeviceUi>>(emptyList())
    private val _devices = MutableStateFlow<List<DeviceUi>>(emptyList())
    val devices: StateFlow<List<DeviceUi>> = _devices

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

    var searchText by mutableStateOf("")
        private set

    var selectedCategory by mutableStateOf("Alle Geräte")
        private set

    init {
        loadDevices()
    }

    fun loadDevices() {
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

    fun toggleFavorite(deviceUi: DeviceUi) {
        repo.toggleFavorite(deviceUi.device)
        _devices.value = _devices.value.map {
            it.copy(isFavorited = repo.isFavorite(it.device))
        }
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
