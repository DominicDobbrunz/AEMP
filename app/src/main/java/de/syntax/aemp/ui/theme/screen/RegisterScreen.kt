package de.syntax.aemp.ui.theme.screen

import android.widget.Toast
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import de.syntax.aemp.FirebaseRepository
import de.syntax.aemp.UserProfile

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit) {
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

    Column(Modifier.verticalScroll(rememberScrollState()).padding(16.dp)) {
        Text("Registrieren", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(value = firstName, onValueChange = { firstName = it }, label = { Text("Vorname") })
        OutlinedTextField(value = lastName, onValueChange = { lastName = it }, label = { Text("Nachname") })
        OutlinedTextField(value = praxisName, onValueChange = { praxisName = it }, label = { Text("Praxisname") })
        OutlinedTextField(value = street, onValueChange = { street = it }, label = { Text("Straße") })
        OutlinedTextField(value = postalCode, onValueChange = { postalCode = it }, label = { Text("PLZ") })
        OutlinedTextField(value = city, onValueChange = { city = it }, label = { Text("Ort") })
        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("E-Mail") })
        OutlinedTextField(value = password, onValueChange = { password = it }, label = { Text("Passwort") }, visualTransformation = PasswordVisualTransformation())
        OutlinedTextField(value = passwordRepeat, onValueChange = { passwordRepeat = it }, label = { Text("Passwort wiederholen") }, visualTransformation = PasswordVisualTransformation())

        Spacer(Modifier.height(16.dp))

        Button(onClick = {
            if (password != passwordRepeat) {
                Toast.makeText(context, "Passwörter stimmen nicht überein", Toast.LENGTH_SHORT).show()
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
                    onRegisterSuccess()
                }
        }) {
            Text("Registrieren")
        }
    }
}