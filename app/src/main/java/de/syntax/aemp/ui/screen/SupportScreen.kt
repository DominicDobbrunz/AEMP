package de.syntax.aemp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import de.syntax.aemp.R

@Composable
fun SupportScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_doppelt_links_48),
                    contentDescription = "Zurück",
                    tint = Color.White
                )
            }
            Text(
                text = "Support",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Beispielinhalt
        Text(
            text = "Wir helfen dir gerne weiter!\n" +
                    "\n" +
                    "\uD83D\uDCE7 E-Mail Support\n" +
                    "Bei Fragen, Problemen oder Feedback kannst du uns jederzeit per E-Mail erreichen:\n" +
                    "support@deineapp.de\n" +
                    "\n" +
                    "\uD83D\uDCD6 Hilfe & FAQ\n" +
                    "Antworten auf häufig gestellte Fragen findest du in unserem Hilfe-Bereich:\n" +
                    "www.deineapp.de/hilfe\n" +
                    "\n" +
                    "\uD83D\uDCA1 Feature-Vorschläge\n" +
                    "Du hast Ideen oder Wünsche für neue Funktionen? Schreib uns – wir freuen uns über dein Feedback!\n" +
                    "\n" +
                    "\uD83D\uDCF1 App-Version\n" +
                    "Version: 1.0.0 (Build 100)\n" +
                    "\n" +
                    "Vielen Dank, dass du unsere App nutzt!",
            color = Color.White
        )
    }
}