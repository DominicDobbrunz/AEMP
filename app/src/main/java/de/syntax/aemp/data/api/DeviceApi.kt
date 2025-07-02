package de.syntax.aemp.data.api

import retrofit2.Response
import de.syntax.aemp.data.model.Device
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface DeviceApi {
    @GET("geraete")
    suspend fun getDevices(): List<Device>

    @GET("geraete/{id}")
    suspend fun getDevice(@Path("id") id: Int): Device

    @POST("geraete")
    suspend fun addDevice(@Body device: Device): Response<Void>

    @DELETE("geraete/{id}")
    suspend fun deleteDevice(@Path("id") id: Int): Response<Void>
}
