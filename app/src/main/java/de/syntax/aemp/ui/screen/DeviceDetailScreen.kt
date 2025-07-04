package de.syntax.aemp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
            val device = deviceUi.device
            AsyncImage(
                model = "http://10.0.2.2:8080/${device.image}",
                contentDescription = "Gerätebild",
                modifier = Modifier
                    .width(350.dp)
                    .height(250.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.LightGray),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = device.name,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text("Kategorie: ${ device.category }", color = Color.White)
            Text("Beschreibung: ${ device.details }", color = Color.White)
            Text("Geräte-ID: ${ device.id }", color = Color.White)
        }
    }
}

