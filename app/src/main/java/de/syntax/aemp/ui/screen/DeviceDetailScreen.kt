package de.syntax.aemp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.syntax.aemp.ui.viewModel.DentalViewModel

@Composable
fun DeviceDetailScreen(kNumber: String?, viewModel: DentalViewModel) {
    val devices = viewModel.devices.collectAsState().value
    val device = devices.find { it.kNumber == kNumber }

    Column(modifier = Modifier.padding(16.dp)) {
        if (device == null) {
            Text("Gerät nicht gefunden", style = MaterialTheme.typography.titleMedium)
        } else {
            Text(device.deviceName ?: "—", style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(8.dp))
            Text("Hersteller: ${device.applicant ?: "—"}")
            Text("Datum: ${device.decisionDate ?: "—"}")
            Text("Regulationsnr.: ${device.regulationNumber ?: "—"}")
            Text("K‑Nummer: ${device.kNumber ?: "—"}")
        }
    }
}