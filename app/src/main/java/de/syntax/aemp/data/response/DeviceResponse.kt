package de.syntax.aemp.data.response

import com.squareup.moshi.JsonClass
import de.syntax.aemp.data.model.Device

@JsonClass(generateAdapter = true)
data class DeviceResponse(
    val results: List<Device>
)