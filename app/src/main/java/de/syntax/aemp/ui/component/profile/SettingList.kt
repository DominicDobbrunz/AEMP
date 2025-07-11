package de.syntax.aemp.ui.component.profile

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController


@Composable
fun SettingList(navController: NavHostController) {

    val settingsItems = listOf(
        "Einstellungen" to { navController.navigate("settings_advanced") },
        "Account" to { navController.navigate("account") },
        "Information" to { navController.navigate("information") },
        "Support" to { navController.navigate("support") },
        "Datenschutz" to { navController.navigate("privacy") }
    )
    settingsItems.forEach { (label, onClick) ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
        ) {
            Text(
                text = label,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color.White
            )
        }
    }
}