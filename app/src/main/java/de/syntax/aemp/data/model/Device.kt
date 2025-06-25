package de.syntax.aemp.data.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Device(
    val deviceName: String?,
    val applicant: String?,
    val decisionDate: String?,
    val regulationNumber: String?,
    val kNumber: String?
)