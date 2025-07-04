package de.syntax.aemp.helper

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

fun getTempCameraUri(context: Context): Uri {
    val imageFile = File.createTempFile("profile_", ".jpg", context.cacheDir).apply {
        createNewFile()
        deleteOnExit()
    }

    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        imageFile
    )
}