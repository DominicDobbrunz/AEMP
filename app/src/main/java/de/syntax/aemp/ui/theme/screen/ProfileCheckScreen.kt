package de.syntax.aemp.ui.theme.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import de.syntax.aemp.FirebaseRepository

@Composable
fun ProfileCheckScreen(
    onProfileExists: () -> Unit,
    onProfileMissing: () -> Unit
) {
    var checked by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        FirebaseRepository.getUserProfile { profile ->
            if (profile == null || profile.praxisName.isBlank()) {
                onProfileMissing()
            } else {
                onProfileExists()
            }
            checked = true
        }
    }

    if (!checked) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}