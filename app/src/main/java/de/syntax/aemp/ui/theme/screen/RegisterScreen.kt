package de.syntax.aemp.ui.theme.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import de.syntax.aemp.data.repository.FirebaseRepository
import de.syntax.aemp.data.model.UserProfile
import de.syntax.aemp.ui.theme.composable.BackgroundImage

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Registrieren") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Zurück")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            BackgroundImage()
            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)

            ) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("Vorname") })
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Nachname") })
                OutlinedTextField(
                    value = praxisName,
                    onValueChange = { praxisName = it },
                    label = { Text("Praxisname") })
                OutlinedTextField(
                    value = street,
                    onValueChange = { street = it },
                    label = { Text("Straße") })
                OutlinedTextField(
                    value = postalCode,
                    onValueChange = { postalCode = it },
                    label = { Text("PLZ") })
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Ort") })
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("E-Mail") })
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Passwort") },
                    visualTransformation = PasswordVisualTransformation()
                )
                OutlinedTextField(
                    value = passwordRepeat,
                    onValueChange = { passwordRepeat = it },
                    label = { Text("Passwort wiederholen") },
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(Modifier.height(16.dp))

                Button(onClick = {
                    if (password != passwordRepeat) {
                        Toast.makeText(
                            context,
                            "Passwörter stimmen nicht überein",
                            Toast.LENGTH_SHORT
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

                            // 🔽 Navigiere zum MainScreen
                            navController.navigate("device_list") {
                                popUpTo("register") { inclusive = true }
                            }
                        }
                }) {
                    Text("Registrieren")
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextButton(onClick = { navController.popBackStack("login", inclusive = false) }) {
                    Text("Zurück zum Login")
                }
            }
        }
    }
}
