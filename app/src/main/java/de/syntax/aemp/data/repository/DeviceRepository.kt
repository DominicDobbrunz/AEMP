package de.syntax.aemp.data.repository

import de.syntax.aemp.data.model.Device
import de.syntax.aemp.data.api.RetrofitInstance

class DeviceRepository {

    private val favorites = mutableSetOf<Int>() // ID statt kNumber

    suspend fun fetchDevices(): List<Device> {
        return RetrofitInstance.api.getDevices()
    }

    fun getFavoriteDevices(allDevices: List<Device>): List<Device> {
        return allDevices.map {
            it
        }
    }

    fun toggleFavorite(device: Device) {
        if (favorites.contains(device.id)) {
            favorites.remove(device.id)
        } else {
            favorites.add(device.id)
        }
    }

    fun isFavorite(device: Device): Boolean {
        return favorites.contains(device.id)
    }
}
