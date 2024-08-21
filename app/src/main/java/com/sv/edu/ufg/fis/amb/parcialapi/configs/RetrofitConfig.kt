package com.sv.edu.ufg.fis.amb.parcialapi.configs

import com.sv.edu.ufg.fis.amb.parcialapi.services.InewsApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitConfig {

    private const val BASE_URL = "https://newsapi.org/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : InewsApiService by lazy{
        retrofit.create(InewsApiService::class.java)
    }

}