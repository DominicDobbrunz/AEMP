package de.syntax.aemp.helper

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.firestore
import de.syntax.aemp.data.repository.StorageRepository

fun uploadImage(user: FirebaseUser?, uri: Uri, context: Context) {
    val uid = user?.uid ?: return
    StorageRepository.uploadProfileImage(uid, uri) { url ->
        if (url != null) {
            Firebase.firestore.collection("users").document(uid)
                .update("profileImageUrl", url)
        } else {
            Toast.makeText(context, "Fehler beim Hochladen", Toast.LENGTH_SHORT).show()
        }
    }
}