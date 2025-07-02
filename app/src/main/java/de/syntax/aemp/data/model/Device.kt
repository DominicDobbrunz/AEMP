package de.syntax.aemp.data.model

import com.squareup.moshi.JsonClass
/*
@JsonClass(generateAdapter = true)
data class Device(
    val deviceName: String?,
    val applicant: String?,
    val decisionDate: String?,
    val regulationNumber: String?,
    val kNumber: String?
)

 */

data class Device(
    val id: Int,
    val name: String,
    val category: String,
    val details: String,
    val image: String
)
