package de.syntax.aemp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import de.syntax.aemp.ui.component.DentalDeviceList
import de.syntax.aemp.ui.viewModel.DentalViewModel


@Composable
fun DentalScreen(navController: NavController, viewModel: DentalViewModel = viewModel()) {
    val error by viewModel.error.collectAsState()

    Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
        Text("Geräte",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(Modifier.height(16.dp))

            if (error != null) {
                Text("❌ $error", color = Color.Red)
            }

            DentalDeviceList(navController, viewModel)

            Button(
                onClick = { viewModel.loadDevices() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mehr laden")
            }
        }
}

