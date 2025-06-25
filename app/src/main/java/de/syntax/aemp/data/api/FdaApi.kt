package de.syntax.aemp.data.api

import de.syntax.aemp.data.response.DeviceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FdaApi {
    @GET("device/510k.json")
    suspend fun getDentalDevices(
        @Query("search") search: String = "device_name:dental",
        @Query("limit") limit: Int = 20,
        @Query("skip") skip: Int = 0
    ): DeviceResponse
}
