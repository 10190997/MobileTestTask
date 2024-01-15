package com.example.mobiletesttask.network

import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("docs")
    var movies: List<Movie>
)
