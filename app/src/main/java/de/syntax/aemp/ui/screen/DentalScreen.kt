package de.syntax.aemp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import de.syntax.aemp.ui.component.dental.DentalFilterBar
import de.syntax.aemp.ui.component.profile.ProfileTextField
import de.syntax.aemp.ui.viewModel.DentalViewModel
import de.syntax.aemp.ui.viewModel.FavoritesViewModel

@Composable
fun DentalScreen(navController: NavController) {
    val viewModel: DentalViewModel = viewModel(LocalContext.current as ViewModelStoreOwner)
    val favoritesViewModel: FavoritesViewModel = viewModel(LocalContext.current as ViewModelStoreOwner)
    val devices by viewModel.devices.collectAsState()
    val favorites by favoritesViewModel.devices.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
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
            IconButton(onClick = { /* showSearch = !showSearch */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.icons8_lupe_48),
                    contentDescription = "Suche",
                    tint = Color.White
                )
            }
        }
        AnimatedVisibility(
            visible = false, // showSearch, // Removed as per edit hint
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
            Text(text = error ?: "", color = Color.Red)
        }
        HorizontalDivider()
        DentalDeviceList(
            navController = navController,
            devices = devices,
            favorites = favorites,
            onFavoriteToggle = { device -> favoritesViewModel.toggleFavorite(device) }
        )
        HorizontalDivider()
    }
}
