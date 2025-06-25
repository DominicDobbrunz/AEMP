package de.syntax.aemp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import de.syntax.aemp.data.repository.FirebaseRepository
import de.syntax.aemp.data.model.UserProfile
import de.syntax.aemp.ui.component.ProfileTextField

@Composable
fun CompleteProfileScreen(onComplete: () -> Unit) {
    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    val currentUser = Firebase.auth.currentUser
    var praxisName by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }

    Column(Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
        Text("Praxisdaten ergänzen", style = MaterialTheme.typography.titleLarge)

        ProfileTextField(value = firstName, label = "Vorname") { firstName = it }
        ProfileTextField(value = lastName, label = "Nachname") { lastName = it }
        OutlinedTextField(value = praxisName, onValueChange = { praxisName = it }, label = { Text("Praxisname") })
        OutlinedTextField(value = street, onValueChange = { street = it }, label = { Text("Straße") })
        OutlinedTextField(value = postalCode, onValueChange = { postalCode = it }, label = { Text("PLZ") })
        OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("Ort") })

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            currentUser?.let { user ->
                val profile = UserProfile(
                    uid = user.uid,
                    firstName = user.displayName?.split(" ")?.firstOrNull() ?: "",
                    lastName = user.displayName?.split(" ")?.lastOrNull() ?: "",
                    praxisName = praxisName,
                    street = street,
                    city = city,
                    postalCode = postalCode,
                    email = user.email ?: ""
                )
                FirebaseRepository.saveUserProfile(profile)
                onComplete()
            }
        }) {
            Text("Speichern")
        }
    }
}