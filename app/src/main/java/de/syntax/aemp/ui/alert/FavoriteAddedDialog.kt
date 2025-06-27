package de.syntax.aemp.ui.alert

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun FavoriteAddedDialog(
    onDismiss: () -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("OK", color = MaterialTheme.colorScheme.onSecondary)
            }
        },
        title = {
            Text("Hinzugefügt", color = MaterialTheme.colorScheme.onSecondary)
        },
        text = {
            Text("Gerät wurde zu deinen Favoriten hinzugefügt", color = MaterialTheme.colorScheme.onSecondary)
        },
        containerColor = backgroundColor,
        shape = RoundedCornerShape(8.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun FavoriteAddedDialogPreview() {
    FavoriteAddedDialog {  }
}