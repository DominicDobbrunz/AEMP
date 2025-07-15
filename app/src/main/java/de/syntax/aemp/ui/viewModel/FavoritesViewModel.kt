package de.syntax.aemp.ui.viewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import de.syntax.aemp.data.model.Device
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FavoritesViewModel : ViewModel() {

    private val _favorites = MutableStateFlow<List<Device>>(emptyList())
    val devices: StateFlow<List<Device>> = _favorites.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    init {
        loadFavorites()
    }

    private fun getUserId(): String? = auth.currentUser?.uid

    fun loadFavorites() {
        val userId = getUserId() ?: return
        db.collection("users")
            .document(userId)
            .collection("favorites")
            .get()
            .addOnSuccessListener { snapshot ->
                val favorites = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Device::class.java)
                }
                _favorites.value = favorites
            }
            .addOnFailureListener {
                _error.value = "Fehler beim Laden der Favoriten: ${it.message}"
            }
    }

    fun addFavorite(device: Device) {
        val userId = getUserId() ?: return
        val docRef = db.collection("users").document(userId).collection("favorites").document(device.id.toString())
        docRef.set(device)
        if (_favorites.value.none { it.id == device.id }) {
            _favorites.value += device
        }
    }

    fun removeFavorite(device: Device) {
        val userId = getUserId() ?: return
        val docRef = db.collection("users").document(userId).collection("favorites").document(device.id.toString())
        docRef.delete()
        _favorites.value = _favorites.value.filterNot { it.id == device.id }
    }

    fun toggleFavorite(device: Device) {
        if (isFavorite(device)) {
            removeFavorite(device)
        } else {
            addFavorite(device)
        }
    }

    fun isFavorite(device: Device): Boolean {
        return _favorites.value.any { it.id == device.id }
    }
}