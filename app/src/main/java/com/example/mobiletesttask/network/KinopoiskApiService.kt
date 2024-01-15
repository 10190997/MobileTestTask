package com.example.mobiletesttask.network

import com.example.mobiletesttask.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

interface KinopoiskApiService {
    @Headers("X-API-KEY: " + Constants.API_KEY)
    @GET("v1.4/movie")
    suspend fun getAllMovies(): Response

    @Headers("X-API-KEY: " + Constants.API_KEY)
    @GET("v1.4/movie/{id}")
    suspend fun getMovieById(
        @Path("id")
        id: Int
    ): Movie

    @Headers("X-API-KEY: " + Constants.API_KEY)
    @GET("v1.4/movie")
    fun searchMovie(
        @Query("query")
        searchQuery: String
    ): List<Movie>
}

object KinopoiskApi {
    val retrofitService : KinopoiskApiService by lazy {
        retrofit.create(KinopoiskApiService::class.java) }
}