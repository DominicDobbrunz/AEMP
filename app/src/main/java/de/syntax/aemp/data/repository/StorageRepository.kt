package de.syntax.aemp.data.repository

import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.storage.storage

object StorageRepository {
    private val storage = Firebase.storage

    fun uploadProfileImage(uid: String, uri: Uri, onSuccess: (String) -> Unit) {
        val ref = storage.reference.child("profile_images/$uid.jpg")
        ref.putFile(uri)
            .continueWithTask { ref.downloadUrl }
            .addOnSuccessListener { downloadUrl ->
                onSuccess(downloadUrl.toString())
            }
    }
}