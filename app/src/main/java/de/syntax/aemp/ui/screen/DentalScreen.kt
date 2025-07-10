package de.syntax.aemp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import de.syntax.aemp.R
import de.syntax.aemp.ui.component.dental.DentalDeviceList
import de.syntax.aemp.ui.component.profile.ProfileTextField
import de.syntax.aemp.ui.viewModel.DentalViewModel
import de.syntax.aemp.ui.viewModel.FavoritesViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import de.syntax.aemp.ui.component.dental.DentalFilterBar

@Composable
fun DentalScreen(
    navController: NavController,
    viewModel: DentalViewModel = viewModel(),
    favoritesViewModel: FavoritesViewModel = viewModel(LocalContext.current as ViewModelStoreOwner)
) {
    val error by viewModel.error.collectAsState()
    val devices by viewModel.devices.collectAsState()

    var showSearch by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Geräte",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge
            )
            IconButton(onClick = { showSearch = !showSearch }) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_lupe_48),
                    contentDescription = "Suche",
                    tint = Color.White
                )
            }
        }
        AnimatedVisibility(
            visible = showSearch,
            enter = slideInVertically(initialOffsetY = { -50 }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { -50 }) + fadeOut()
        ) {
            ProfileTextField(
                value = viewModel.searchText,
                label = "Geräte suchen",
                onValueChange = { viewModel.onSearchTextChange(it) }
            )
        }
        Spacer(Modifier.height(8.dp))
        DentalFilterBar(
            selectedCategory = viewModel.selectedCategory,
            onCategorySelected = { viewModel.onCategorySelected(it) }
        )
        if (error != null) {
            Text("❌ $error", color = Color.Red)
        }
        DentalDeviceList(
            navController = navController,
            devices = devices,
            selectedCategory = viewModel.selectedCategory,
            onCategorySelected = { viewModel.onCategorySelected(it) },
            onFavoriteToggle = { viewModel.toggleFavorite(it) },
            favoritesViewModel = favoritesViewModel
        )
    }
}
