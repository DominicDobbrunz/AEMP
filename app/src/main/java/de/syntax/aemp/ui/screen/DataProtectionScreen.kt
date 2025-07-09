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
fun DataProtectionScreen(navController: NavHostController) {
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
                text = "Datenschutz",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Der Schutz deiner Daten ist uns wichtig. Nachfolgend informieren wir dich über die wichtigsten Punkte zur Datenverarbeitung in unserer App.\n" +
                    "\n" +
                    "1. Verantwortliche Stelle\n" +
                    "Firma: AEMP  \n" +
                    "Adresse:  \n" +
                    "Kontakt-E-Mail:\n" +
                    "\n" +
                    "2. Welche Daten werden erfasst?\n" +
                    "- Nutzungsdaten (z. B. besuchte Seiten, Interaktionen)\n" +
                    "- Geräteinformationen (z. B. Modell, Betriebssystem)\n" +
                    "- Bei Kontaktaufnahme: deine E-Mail-Adresse\n" +
                    "\n" +
                    "3. Wofür werden die Daten genutzt?\n" +
                    "- Zur Verbesserung der App\n" +
                    "- Zur Fehleranalyse\n" +
                    "- Zur Kommunikation bei Supportanfragen\n" +
                    "\n" +
                    "4. Werden Daten weitergegeben?\n" +
                    "Deine Daten werden nicht an Dritte verkauft. Eine Weitergabe erfolgt nur, wenn dies gesetzlich erlaubt ist oder zur Erfüllung unserer Dienste erforderlich ist (z. B. Hosting-Anbieter).\n" +
                    "\n" +
                    "5. Deine Rechte\n" +
                    "Du hast das Recht auf:\n" +
                    "- Auskunft über deine gespeicherten Daten\n" +
                    "- Berichtigung oder Löschung\n" +
                    "- Einschränkung der Verarbeitung\n" +
                    "- Widerspruch gegen die Datenverarbeitung\n" +
                    "- Datenübertragbarkeit\n" +
                    "\n" +
                    "6. Kontakt zum Datenschutz\n" +
                    "Für Anfragen zum Datenschutz wende dich an:  \n" +
                    "datenschutz@deineapp.de\n" +
                    "\n" +
                    "Weitere Informationen findest du unter:  \n" +
                    "www.deineapp.de/datenschutz",
            color = Color.White
        )
    }
}
