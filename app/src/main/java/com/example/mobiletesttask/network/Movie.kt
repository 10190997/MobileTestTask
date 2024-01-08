package com.example.mobiletesttask.network

data class Movie(
    val id: Int,
    val name: String,
    val poster: Poster,
    val countries: List<Country>,
    val description: String,
    val genres: List<Genre>,
    val year: Int
)