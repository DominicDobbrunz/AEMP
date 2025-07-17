package de.syntax.aemp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import de.syntax.aemp.R
import de.syntax.aemp.data.model.UserProfile
import de.syntax.aemp.data.repository.FirebaseRepository


@Composable
fun AccountScreen(
    navController: NavHostController
) {
    val user = Firebase.auth.currentUser
    val context = LocalContext.current
    var profile by remember { mutableStateOf<UserProfile?>(null) }
    var birthDateInput by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        FirebaseRepository.getUserProfile {
            profile = it
            birthDateInput = it?.birthDate ?: ""
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // 🔙 Back & Title
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_doppelt_links_48),
                    contentDescription = "Zurück",
                    tint = Color.White
                )
            }
            Text(
                text = "Account",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        // 👤 Benutzerinformationen
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Name", style = MaterialTheme.typography.labelMedium, color = Color.White)
                Text(
                    text = profile?.let { "${it.firstName} ${it.lastName}" } ?: (user?.displayName ?: "Nicht verfügbar"),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Geburtsdatum", style = MaterialTheme.typography.labelMedium, color = Color.White)
                if (profile?.birthDate.isNullOrBlank()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = birthDateInput,
                            onValueChange = { birthDateInput = it },
                            label = { Text("Geburtsdatum") },
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = {
                            profile?.let {
                                val updated = it.copy(birthDate = birthDateInput)
                                FirebaseRepository.saveUserProfile(updated)
                                profile = updated
                            }
                        }) {
                            Text("Speichern")
                        }
                    }
                } else {
                    Text(
                        text = profile?.birthDate ?: "-",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text("E-Mail", style = MaterialTheme.typography.labelMedium, color = Color.White)
                Text(
                    text = user?.email ?: "Nicht verfügbar",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // ✉️ E-Mail Verifizierung
        if (user != null && !user.isEmailVerified) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "E-Mail nicht verifiziert!",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(onClick = {
                        user.sendEmailVerification()
                        Toast.makeText(context, "Verifizierungslink gesendet", Toast.LENGTH_SHORT).show()
                    }) {
                        Text("E-Mail verifizieren")
                    }
                }
            }
        }
    }
}