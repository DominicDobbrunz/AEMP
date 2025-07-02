package de.syntax.aemp.data.repository

import de.syntax.aemp.data.model.Device
import de.syntax.aemp.data.api.RetrofitInstance
import de.syntax.aemp.data.model.DeviceUi
/*
class DeviceRepository {

    // Lokale Favoriten-Speicherung (z. B. In-Memory)
    private val favorites = mutableSetOf<String>() // kNumber oder ID als Schlüssel

    suspend fun fetchDevices(query: String = "", skip: Int = 0): List<Device> {
        val resp = RetrofitInstance.api.getDentalDevices(search = query, skip = skip)
        return resp.results
    }

    fun getFavoriteDevices(allDevices: List<Device>): List<DeviceUi> {
        return allDevices.map { device ->
            DeviceUi(device = device, isFavorited = favorites.contains(device.kNumber))
        }
    }

    fun toggleFavorite(device: Device) {
        val key = device.kNumber
        if (favorites.contains(key)) {
            favorites.remove(key)
        } else {
            if (key != null) {
                favorites.add(key)
            }
        }
    }

    fun isFavorite(device: Device): Boolean {
        return favorites.contains(device.kNumber)
    }
}

 */

class DeviceRepository {

    private val favorites = mutableSetOf<Int>() // ID statt kNumber

    suspend fun fetchDevices(): List<Device> {
        return RetrofitInstance.api.getDevices()
    }

    fun getFavoriteDevices(allDevices: List<Device>): List<DeviceUi> {
        return allDevices.map {
            DeviceUi(it, favorites.contains(it.id))
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
