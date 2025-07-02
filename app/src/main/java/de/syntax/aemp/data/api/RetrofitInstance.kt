package de.syntax.aemp.data.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
/*
object RetrofitInstance {
    private const val BASE_URL = "https://api.fda.gov/"
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    val api: FdaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(FdaApi::class.java)
    }
}

 */

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val api: DeviceApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeviceApi::class.java)
    }
}
