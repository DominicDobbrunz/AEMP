package de.syntax.aemp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import de.syntax.aemp.R
import de.syntax.aemp.ui.viewModel.DentalViewModel
/*
@Composable
fun DeviceDetailScreen(
    kNumber: String?,
    viewModel: DentalViewModel,
    navController: NavController
) {
    val devices = viewModel.devices.collectAsState().value
    val device = devices.find { it.kNumber == kNumber }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_doppelt_links_48),
                    contentDescription = "Zurück",
                    tint = Color.White,
                    modifier = Modifier
                )
            }
            Text("Details",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (device == null) {
            Text(
                "Gerät nicht gefunden",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            Text(
                device.deviceName ?: "—",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Spacer(Modifier.height(8.dp))
            Text("Hersteller: ${device.applicant ?: "—"}", color = Color.White)
            Text("Datum: ${device.decisionDate ?: "—"}", color = Color.White)
            Text("Regulationsnr.: ${device.regulationNumber ?: "—"}", color = Color.White)
            Text("K‑Nummer: ${device.kNumber ?: "—"}", color = Color.White)
        }
    }
}

 */

@Composable
fun DeviceDetailScreen(
    id: Int,
    viewModel: DentalViewModel,
    navController: NavController
) {
    val devices = viewModel.devices.collectAsState().value
    val deviceUi = devices.find { it.device.id == id } // ✅ Korrekt

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // 🔙 Zurück
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_doppelt_links_48),
                    contentDescription = "Zurück",
                    tint = Color.White
                )
            }
            Text(
                "Details",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (deviceUi == null) {
            Text(
                "Gerät nicht gefunden",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
        } else {
            val device = deviceUi.device // ✅ Zugriff auf das eigentliche Gerät

            // 📷 Gerätebild oben
            AsyncImage(
                model = "http://10.0.2.2:8080/${device.image}",
                contentDescription = "Gerätebild",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // ℹ️ Geräteinfos
            Text(
                text = device.name ?: "Unbekanntes Gerät",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text("Kategorie: ${device.category ?: "—"}", color = Color.White)
            Text("Beschreibung: ${device.details ?: "—"}", color = Color.White)
            Text("Geräte-ID: ${device.id}", color = Color.White)
        }
    }
}

