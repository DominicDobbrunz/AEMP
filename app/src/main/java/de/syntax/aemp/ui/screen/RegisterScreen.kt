package de.syntax.aemp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import de.syntax.aemp.data.model.UserProfile
import de.syntax.aemp.data.repository.FirebaseRepository
import de.syntax.aemp.ui.component.profile.PasswordField
import de.syntax.aemp.ui.component.profile.ProfileTextField

@Composable
fun RegisterScreen(
    navController: NavHostController,
    onRegisterSuccess: () -> Unit
) {
    val context = LocalContext.current
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var praxisName by remember { mutableStateOf("") }
    var street by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordRepeat by remember { mutableStateOf("") }

    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)

    ) {
        Text("Registrieren",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
            )
        Spacer(Modifier.height(16.dp))
        ProfileTextField(value = firstName, label = "Vorname") { firstName = it }
        ProfileTextField(value = lastName, label = "Nachname") { lastName = it }
        ProfileTextField(value = praxisName, label = "Praxisname") { praxisName = it }
        ProfileTextField(value = street, label = "Straße") { street = it }
        ProfileTextField(value = postalCode, label = "PLZ") { postalCode = it }
        ProfileTextField(value = city, label = "Ort") { city = it }
        ProfileTextField(value = email, label = "E-Mail") { email = it }
        PasswordField(
            password = password,
            onPasswordChange = { password = it },
            modifier = Modifier
        )
        PasswordField(
            password = passwordRepeat,
            onPasswordChange = { passwordRepeat = it },
            modifier = Modifier
        )

        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                if (firstName.isBlank() || lastName.isBlank() || praxisName.isBlank() || street.isBlank() || city.isBlank() || postalCode.isBlank() || email.isBlank() || password.isBlank() || passwordRepeat.isBlank()) {
                    Toast.makeText(
                        context,
                        "Bitte alle Felder ausfüllen",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                if (password != passwordRepeat) {
                    Toast.makeText(
                        context,
                        "Passwörter stimmen nicht überein",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@Button
                }
                val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}")
                if (!passwordPattern.matches(password)) {
                    Toast.makeText(
                        context,
                        "Passwort zu schwach: Mindestens 8 Zeichen, Groß- und Kleinbuchstaben sowie eine Zahl erforderlich",
                        Toast.LENGTH_LONG
                    ).show()
                    return@Button
                }
                Firebase.auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { result ->
                        val uid = result.user?.uid ?: return@addOnSuccessListener
                        val profile = UserProfile(
                            uid = uid,
                            firstName = firstName,
                            lastName = lastName,
                            praxisName = praxisName,
                            street = street,
                            city = city,
                            postalCode = postalCode,
                            email = email
                        )
                        FirebaseRepository.saveUserProfile(profile)
                        result.user?.sendEmailVerification()
                        navController.navigate("dentals") {
                            popUpTo("register") { inclusive = true }
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            context,
                            "Registrierung fehlgeschlagen: ${e.localizedMessage}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrieren")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { navController.popBackStack("login", inclusive = false) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Zurück zum Login")
        }
    }
}

