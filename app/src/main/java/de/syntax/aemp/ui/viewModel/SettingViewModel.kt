package de.syntax.aemp.ui.viewModel

import android.app.Application
import android.net.Uri
import androidx.datastore.preferences.core.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import de.syntax.aemp.data.extension.dataStore
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SettingViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext
    private val dataStore = context.dataStore

    private val auth = Firebase.auth
    private val storage = Firebase.storage
    private val firestore = Firebase.firestore

    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImageUrl: StateFlow<String?> = _profileImageUrl.asStateFlow()

    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(true)
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()

    private val _soundEnabled = MutableStateFlow(false)
    val soundEnabled: StateFlow<Boolean> = _soundEnabled.asStateFlow()

    private val _vibrationEnabled = MutableStateFlow(false)
    val vibrationEnabled: StateFlow<Boolean> = _vibrationEnabled.asStateFlow()

    init {
        loadSettings()
        loadProfileImage()
    }

    private fun getUserId(): String? = auth.currentUser?.uid

    // 🔹 Firebase: Profilbild laden
    private fun loadProfileImage() {
        val userId = getUserId() ?: return
        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { doc ->
                _profileImageUrl.value = doc.getString("profileImageUrl")
            }
    }

    // 🔹 Firebase: Profilbild speichern
    fun uploadProfileImage(uri: Uri) {
        val userId = getUserId() ?: return
        val ref = storage.reference.child("profile_images/$userId.jpg")

        ref.putFile(uri)
            .continueWithTask { ref.downloadUrl }
            .addOnSuccessListener { downloadUri ->
                _profileImageUrl.value = downloadUri.toString()
                firestore.collection("users").document(userId)
                    .set(mapOf("profileImageUrl" to downloadUri.toString()), SetOptions.merge())
            }
    }

    // 🔸 Lokale Einstellungen laden
    private fun loadSettings() {
        viewModelScope.launch {
            dataStore.data.firstOrNull()?.let {
                _isDarkMode.value = it[booleanPreferencesKey("dark_mode")] ?: false
                _notificationsEnabled.value = it[booleanPreferencesKey("notifications_enabled")] ?: true
                _soundEnabled.value = it[booleanPreferencesKey("sound_enabled")] ?: false
                _vibrationEnabled.value = it[booleanPreferencesKey("vibration_enabled")] ?: false
            }
        }
    }

    // 🔸 Dark Mode speichern
    fun setDarkMode(enabled: Boolean) {
        _isDarkMode.value = enabled
        viewModelScope.launch {
            dataStore.edit { settings ->
                settings[booleanPreferencesKey("dark_mode")] = enabled
            }
        }
    }

    // 🔸 Benachrichtigungen speichern
    fun setNotificationsEnabled(enabled: Boolean) {
        _notificationsEnabled.value = enabled
        viewModelScope.launch {
            dataStore.edit { settings ->
                settings[booleanPreferencesKey("notifications_enabled")] = enabled
            }
        }
    }

    // 🔸 Töne speichern
    fun setSoundEnabled(enabled: Boolean) {
        _soundEnabled.value = enabled
        viewModelScope.launch {
            dataStore.edit { settings ->
                settings[booleanPreferencesKey("sound_enabled")] = enabled
            }
        }
    }

    // 🔸 Vibration speichern
    fun setVibrationEnabled(enabled: Boolean) {
        _vibrationEnabled.value = enabled
        viewModelScope.launch {
            dataStore.edit { settings ->
                settings[booleanPreferencesKey("vibration_enabled")] = enabled
            }
        }
    }
}