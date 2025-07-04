package de.syntax.aemp.ui.alert

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.FileProvider
import java.io.File


@Composable
fun ImagePickerDialog(
    onImageSelected: (Uri) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    // Für Galerie
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let { onImageSelected(it) }
        onDismiss()
    }

    // Für Kamera
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            photoUri?.let { onImageSelected(it) }
        }
        onDismiss()
    }
    val backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
    fun createImageFileUri(context: Context): Uri {
        val photoFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",  // wichtig!
            photoFile
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        text = {
            Column {
                TextButton(onClick = {
                    galleryLauncher.launch("image/*")
                }) {
                    Text("Aus Galerie auswählen", color = MaterialTheme.colorScheme.onSecondary)
                }
                TextButton(onClick = {
                    val uri = createImageFileUri(context)
                    photoUri = uri
                    takePictureLauncher.launch(uri)
                }) {
                    Text("Mit Kamera aufnehmen", color = MaterialTheme.colorScheme.onSecondary)
                }
            }
        },
        confirmButton = { },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Abbrechen", color = MaterialTheme.colorScheme.onSecondary)
            }
        },
        containerColor = backgroundColor
    )
}