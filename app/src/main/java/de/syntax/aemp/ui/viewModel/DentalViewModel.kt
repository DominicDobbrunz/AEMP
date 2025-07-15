package de.syntax.aemp.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.syntax.aemp.data.model.Device
import de.syntax.aemp.data.repository.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DentalViewModel : ViewModel() {
    private val repo = DeviceRepository()

    private val _allDevices = MutableStateFlow<List<Device>>(emptyList())
    private val _devices = MutableStateFlow<List<Device>>(emptyList())
    val devices: StateFlow<List<Device>> = _devices

    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()

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
                _allDevices.value = list
                applyFilters()
            } catch (e: Exception) {
                _error.value = "Fehler: ${e.message}"
            }
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
            val matchesSearch = device.name.contains(searchText, ignoreCase = true)
            val matchesCategory = selectedCategory == "Alle Geräte" ||
                    device.category.equals(selectedCategory, ignoreCase = true)

            matchesSearch && matchesCategory
        }
        _devices.value = filtered
    }
}
