package de.syntax.aemp.data.repository

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import de.syntax.aemp.data.model.UserProfile

object FirebaseRepository {
    private val db = Firebase.firestore
    private val auth = Firebase.auth

    fun saveUserProfile(profile: UserProfile) {
        db.collection("users").document(profile.uid).set(profile)
    }

    fun getUserProfile(onResult: (UserProfile?) -> Unit) {
        val uid = auth.currentUser?.uid ?: return onResult(null)
        db.collection("users").document(uid).get()
            .addOnSuccessListener { doc ->
                val profile = doc.toObject(UserProfile::class.java)
                onResult(profile)
            }
            .addOnFailureListener { onResult(null) }
    }
}