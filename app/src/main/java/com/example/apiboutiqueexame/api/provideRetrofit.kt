package com.example.apiboutiqueexame.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideRetrofit(): ApiService {
    return Retrofit.Builder()
        .baseUrl("http://10.0.2.2:3000/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}
