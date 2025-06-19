package de.syntax.aemp.data.model

data class UserProfile(
    val uid: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val praxisName: String = "",
    val street: String = "",
    val city: String = "",
    val postalCode: String = "",
    val email: String = "",
    val profileImageUrl: String = ""
)