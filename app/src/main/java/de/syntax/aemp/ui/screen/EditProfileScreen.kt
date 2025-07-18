package de.syntax.aemp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.syntax.aemp.data.model.UserProfile
import de.syntax.aemp.data.repository.FirebaseRepository
import de.syntax.aemp.ui.component.profile.ProfileTextField

@Composable
fun EditProfileScreen(
    onSave: () -> Unit,
    onCancel: () -> Unit = { }
) {
    val context = LocalContext.current
    var profile by remember { mutableStateOf<UserProfile?>(null) }

    LaunchedEffect(Unit) {
        FirebaseRepository.getUserProfile {
            profile = it
        }
    }

    profile?.let { userProfile ->
        var praxisName by remember { mutableStateOf(userProfile.praxisName) }
        var street by remember { mutableStateOf(userProfile.street) }
        var city by remember { mutableStateOf(userProfile.city) }
        var postalCode by remember { mutableStateOf(userProfile.postalCode) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Profil bearbeiten",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            ProfileTextField(value = praxisName, label = "Praxisname") { praxisName = it }
            ProfileTextField(value = street, label = "Straße") { street = it }
            ProfileTextField(value = postalCode, label = "PLZ") { postalCode = it }
            ProfileTextField(value = city, label = "Ort") { city = it }

            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val updated = userProfile.copy(
                        praxisName = praxisName.trim(),
                        street = street.trim(),
                        city = city.trim(),
                        postalCode = postalCode.trim()
                    )
                    FirebaseRepository.saveUserProfile(updated)
                    Toast.makeText(context, "Profil gespeichert", Toast.LENGTH_SHORT).show()
                    onSave()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Speichern")
            }
            Button(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Abbrechen")
            }
        }
    } ?: Box(
        Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}