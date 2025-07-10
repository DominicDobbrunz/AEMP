package de.syntax.aemp.ui.screen

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.syntax.aemp.R
import de.syntax.aemp.data.model.UserProfile
import de.syntax.aemp.data.repository.FirebaseRepository
import de.syntax.aemp.data.repository.StorageRepository
import de.syntax.aemp.ui.alert.ImagePickerDialog
import de.syntax.aemp.ui.alert.LogoutAlert
import de.syntax.aemp.ui.viewModel.SettingViewModel
import de.syntax.aemp.ui.viewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel(),
    settingsViewModel: SettingViewModel = viewModel()
) {
    val user = Firebase.auth.currentUser
    var profile by remember { mutableStateOf<UserProfile?>(null) }
    val context = LocalContext.current
    var showSheet by remember { mutableStateOf(false) }
    var showImagePicker by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var imagePickerRequested by remember { mutableStateOf(false) }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val cameraPermission = Manifest.permission.CAMERA

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            showImagePicker = true
        } else {
            Toast.makeText(context, "Kamera-Berechtigung erforderlich", Toast.LENGTH_SHORT).show()
        }
    }

    fun handleImageSelected(uri: Uri) {
        val uid = user?.uid ?: return
        StorageRepository.uploadProfileImage(uid, uri) { url ->
            if (url != null) {
                Firebase.firestore.collection("users").document(uid)
                    .update("profileImageUrl", url)
            } else {
                Toast.makeText(context, "Fehler beim Hochladen", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(true) {
        FirebaseRepository.getUserProfile {
            profile = it
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            val imageUrl = profile?.profileImageUrl?.takeIf { it.isNotBlank() }
                ?: user?.photoUrl?.toString()?.takeIf { it.isNotBlank() }
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .clickable {
                        imagePickerRequested = true
                        cameraPermissionLauncher.launch(cameraPermission)
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Profilbild",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.icons8_benutzer_100),
                        contentDescription = "Profilbild",
                        tint = Color.White,
                        modifier = Modifier.size(100.dp) // kleiner als der ganze Kreis
                    )
                }
            }
            Spacer(Modifier.height(8.dp))
            Text(
                "${profile?.firstName ?: "Nutzer"} ${profile?.lastName ?: ""}",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
        profile?.let {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top // 👈 das sorgt für vertikale Ausrichtung oben
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Anschrift:", fontWeight = FontWeight.Medium, color = Color.White)
                        Spacer(Modifier.height(8.dp))
                        Text("Praxis: ${it.praxisName}", color = Color.White)
                        Text("Adresse: ${it.street}, ${it.postalCode} ${it.city}", color = Color.White)
                    }
                    IconButton(onClick = { showSheet = true }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Bearbeiten",
                            tint = Color.White
                        )
                    }
                }
            }
        }
        val settingsItems = listOf(
            "Einstellungen" to { navController.navigate("settings_advanced") },
            "Account" to { navController.navigate("account") },
            "Information" to { navController.navigate("information") },
            "Support" to { navController.navigate("support") },
            "Datenschutz" to { navController.navigate("privacy") }
        )
        settingsItems.forEach { (label, onClick) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick() },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
            ) {
                Text(
                    text = label,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Button(
            onClick = { showLogoutDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ausloggen")
        }
        LogoutAlert(
            showDialog = showLogoutDialog,
            onDismiss = { showLogoutDialog = false },
            onConfirmLogout = {
                showLogoutDialog = false
                Firebase.auth.signOut()
                navController.navigate("login") {
                    popUpTo("settings") { inclusive = true }
                    launchSingleTop = true
                }
            }
        )
    }
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f)
        ) {
            EditProfileScreen(
                onSave = { showSheet = false },
                onCancel = { showSheet = false }
            )
        }
    }
    if (imagePickerRequested && showImagePicker) {
        ImagePickerDialog(
            onImageSelected = {
                handleImageSelected(it)
                showImagePicker = false
                imagePickerRequested = false
            },
            onDismiss = {
                showImagePicker = false
                imagePickerRequested = false
            }
        )
    }
}
