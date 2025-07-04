package de.syntax.aemp.helper

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File

object ImageUtils {
    fun saveBitmapToCacheAndGetUri(context: Context, bitmap: Bitmap): Uri {
        val filename = "profile_${System.currentTimeMillis()}.jpg"
        val file = File(context.cacheDir, filename)
        file.outputStream().use { bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it) }
        return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
    }
}