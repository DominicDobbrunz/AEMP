package de.syntax.aemp.ui.screen

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import de.syntax.aemp.data.repository.FirebaseRepository
import de.syntax.aemp.data.repository.StorageRepository
import de.syntax.aemp.data.model.UserProfile
import de.syntax.aemp.ui.viewModel.UserViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController,
    userViewModel: UserViewModel = viewModel()
) {
    val user = Firebase.auth.currentUser
    var profile by remember { mutableStateOf<UserProfile?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val uid = user?.uid ?: return@let
            StorageRepository.uploadProfileImage(uid, it) { url ->
                if (url != null) {
                    Firebase.firestore.collection("users").document(uid)
                        .update("profileImageUrl", url)
                } else {
                    Toast.makeText(context, "Fehler beim Hochladen", Toast.LENGTH_SHORT).show()
                }
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
        // Kopfzeile mit Titel & Bild
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Einstellungen",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
                )
            AsyncImage(
                model = profile?.profileImageUrl ?: user?.photoUrl,
                contentDescription = "Profilbild",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .clickable { launcher.launch("image/*") }
            )
        }

        // Nutzerinfo in transparenter Card
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

        // Einstellungskarten
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

        // E-Mail Verifikation
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

        // Logout unten
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
}