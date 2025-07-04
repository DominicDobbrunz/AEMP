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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import de.syntax.aemp.data.model.UserProfile
import de.syntax.aemp.data.repository.FirebaseRepository
import de.syntax.aemp.data.repository.StorageRepository
import de.syntax.aemp.ui.alert.ImagePickerDialog
import de.syntax.aemp.ui.viewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel()
) {
    val user = Firebase.auth.currentUser
    var profile by remember { mutableStateOf<UserProfile?>(null) }
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var showSheet by remember { mutableStateOf(false) }
    var showImagePicker by remember { mutableStateOf(false) }
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Einstellungen",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    model = profile?.profileImageUrl ?: user?.photoUrl,
                    contentDescription = "Profilbild",
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .clickable {
                            cameraPermissionLauncher.launch(cameraPermission)
                        }
                )
                Spacer(Modifier.width(8.dp))
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Mehr", tint = Color.White)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.primary)
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Profil bearbeiten",
                                    color = Color.White
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.AccountCircle,
                                    contentDescription = "Profil bearbeiten",
                                    tint = Color.White
                                )
                            },
                            onClick = {
                                expanded = false
                                showSheet = true
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "Profilbild hinzufügen",
                                    color = Color.White
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.CameraAlt,
                                    contentDescription = "Kamera",
                                    tint = Color.White
                                )
                            },
                            onClick = {
                                expanded = false
                                cameraPermissionLauncher.launch(cameraPermission)
                            }
                        )
                    }
                }
            }
        }
        profile?.let {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Info von der Person und Praxis Anschrift", fontWeight = FontWeight.Medium, color = Color.White)
                    Spacer(Modifier.height(8.dp))
                    Text("Name: ${it.firstName} ${it.lastName}", color = Color.White)
                    Text("Praxis: ${it.praxisName}", color = Color.White)
                    Text("Adresse: ${it.street}, ${it.postalCode} ${it.city}", color = Color.White)
                    Text("E-Mail: ${it.email}", color = Color.White)
                }
            }
        }
        val settingsItems = listOf(
            "Allgemeine Einstellung" to {},
            "Account" to {},
            "Benachrichtigungen" to {},
            "Information" to {},
            "Support" to {},
            "Datenschutz" to {}
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
        if (user != null && !user.isEmailVerified) {
            Text("E-Mail nicht verifiziert!", color = Color.Red)
            Button(
                onClick = {
                    user.sendEmailVerification()
                    Toast.makeText(context, "Verifizierungslink gesendet", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                Text("E-Mail verifizieren")
            }
        }
        Button(
            onClick = {
                Firebase.auth.signOut()
                navController.navigate("login") {
                    popUpTo("settings") { inclusive = true }
                    launchSingleTop = true
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }
    }
    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = { showSheet = false },
            sheetState = bottomSheetState,
            containerColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f)
        ) {
            EditProfileScreen(
                onSave = { showSheet = false },
                onCancel = { showSheet = false }
            )
        }
    }
    if (showImagePicker) {
        ImagePickerDialog(
            onImageSelected = {
                handleImageSelected(it)
                showImagePicker = false
            },
            onDismiss = { showImagePicker = false }
        )
    }
}