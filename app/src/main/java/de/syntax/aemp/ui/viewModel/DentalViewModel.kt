package de.syntax.aemp.ui.viewModel

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
    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val _devices = MutableStateFlow<List<DeviceUi>>(emptyList())
    val devices: StateFlow<List<DeviceUi>> = _devices
    private val _error = MutableStateFlow<String?>(null)
    val error = _error.asStateFlow()
    private var skip = 0
    private val pageSize = 20
    private var favorites = mutableSetOf<String>()
    init {
        loadFavorites()
        loadDevices()
    }
    private fun loadFavorites() {
        val uid = auth.currentUser?.uid ?: return
        viewModelScope.launch {
            try {
                val favDocs = db.collection("users").document(uid)
                    .collection("favorites").get().await()
                favorites = favDocs.documents.mapNotNull { it.id }.toMutableSet()
                refreshUi()
            } catch (e: Exception) {
                _error.value = "Fehler beim Laden der Favoriten"
            }
        }
    }
    fun loadDevices() {
        viewModelScope.launch {
            try {
                val list = repo.fetchDevices("device_name:dental", skip)
                skip += pageSize
                val newUiList = list.map {
                    DeviceUi(it, favorites.contains(it.kNumber))
                }
                _devices.value += newUiList
            } catch (e: Exception) {
                _error.value = "Laden fehlgeschlagen: ${e.message}"
            }
        }
    }
    fun toggleFavorite(deviceUi: DeviceUi) {
        val uid = auth.currentUser?.uid ?: return
        val kNumber = deviceUi.device.kNumber ?: return
        val favRef = db.collection("users").document(uid)
            .collection("favorites").document(kNumber)
        viewModelScope.launch {
            if (deviceUi.isFavorited) {
                favRef.delete()
                favorites.remove(kNumber)
            } else {
                favRef.set(mapOf("deviceName" to deviceUi.device.deviceName))
                favorites.add(kNumber)
            }
            refreshUi()
        }
    }
    private fun refreshUi() {
        _devices.value = _devices.value.map {
            it.copy(isFavorited = favorites.contains(it.device.kNumber))
        }
    }
    fun searchDevices(query: String) {
        viewModelScope.launch {
            try {
                skip = 0
                val list = repo.fetchDevices("device_name:$query", skip)
                val uiList = list.map {
                    DeviceUi(it, favorites.contains(it.kNumber))
                }
                _devices.value = uiList
            } catch (e: Exception) {
                _error.value = "Dieses Gerät nicht gefunden"
            }
        }
    }
}
