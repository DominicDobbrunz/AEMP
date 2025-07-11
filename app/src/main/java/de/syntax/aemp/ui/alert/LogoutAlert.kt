package de.syntax.aemp.ui.alert

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LogoutAlert(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirmLogout: () -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = onConfirmLogout) {
                    Text("Abmelden", color = MaterialTheme.colorScheme.onSecondary)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Abbrechen", color = MaterialTheme.colorScheme.error)
                }
            },
            title = { Text("Abmelden?") },
            text = { Text("Möchtest du dich wirklich abmelden?") },
            containerColor = backgroundColor,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            textContentColor = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LogoutAlertPreview() {

}