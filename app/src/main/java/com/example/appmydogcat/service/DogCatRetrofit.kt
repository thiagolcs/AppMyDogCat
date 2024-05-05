package com.example.appmydogcat.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DogCatRetrofit {

    private lateinit var instance: Retrofit
    private val baseUrl = "https://api.thecatapi.com/v1/"

    fun getRetrofitInstance(): DogCatService {

        val http = OkHttpClient.Builder()

        instance = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(http.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return instance.create(DogCatService::class.java)
    }

}