package com.example.mjosevl20240512

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ClienteRetrofit {
    private const val BASE_URL = "https://mindicador.cl/api/"

    val servicio: ApiServicio by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiServicio::class.java)
    }
}

