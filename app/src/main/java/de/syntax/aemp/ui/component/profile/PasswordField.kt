package de.syntax.aemp.ui.component.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp


@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val transparentCardColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.5f)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = transparentCardColor),
        shape = RoundedCornerShape(6.dp)
    ) {
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            label = { Text("Passwort") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            visualTransformation = PasswordVisualTransformation()
        )
    }
}