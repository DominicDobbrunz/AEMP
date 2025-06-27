package de.syntax.aemp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.syntax.aemp.R
import de.syntax.aemp.ui.component.app.BackgroundImage
import de.syntax.aemp.ui.component.profile.PasswordField
import de.syntax.aemp.ui.component.profile.ProfileTextField

@Composable
fun LoginScreen(
    onLoginWithGoogle: () -> Unit,
    onLoginWithEmail: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackgroundImage()
        Column(
            Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "AEMP",
                color = Color.White,
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(Modifier.height(16.dp))
            ProfileTextField( value = email, label = "E-Mail") { email = it.trim() }
            PasswordField( password = password, onPasswordChange = {password = it}, modifier = Modifier)

            Spacer(Modifier.height(16.dp))
            Button(
                onClick = {
                    if (email.isBlank() || password.isBlank()) {
                        Toast.makeText(context, "Bitte alle Felder ausfüllen", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        onLoginWithEmail(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onLoginWithGoogle,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_google_logo_48),
                    contentDescription = "Google",
                    modifier = Modifier
                )
                Spacer(Modifier.width(8.dp))
                Text("Mit Google anmelden")
            }
            Spacer(Modifier.height(8.dp))
            TextButton(onClick = onNavigateToRegister) {
                Text("Kein Konto? Jetzt registrieren", color = Color.White)
            }
        }
    }
}
