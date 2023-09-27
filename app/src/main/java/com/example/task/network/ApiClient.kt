package com.example.task.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {
    val okHttpClient = OkHttpClient.Builder()
        .readTimeout(7, TimeUnit.MINUTES)
        .connectTimeout(7, TimeUnit.MINUTES)
        .build()

    fun getRetrofitClient(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder().baseUrl("http://qaapi.osm-erp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
    }
}