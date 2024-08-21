package com.sv.edu.ufg.fis.amb.parcialapi.services

import com.sv.edu.ufg.fis.amb.parcialapi.models.ArticuloResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface InewsApiService {

    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") q : String = "news",
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("language") language: String? = null,
        @Query("apiKey") apiKey: String
    ) : Response<ArticuloResponse>

}