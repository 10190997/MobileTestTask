package com.example.mobiletesttask.network

data class Movie(
    val id: Int,
    val name: String,
    val poster: Poster,
    val countries: List<Country>,
    val description: String,
    val genres: List<Genre>,
    val year: Int
) {
    fun anyContains(query: String?): Boolean {
        return query.isNullOrBlank() ||
                name.contains(query, true) ||
                countries.any { it.name.contains(query, true) } ||
                genres.any { it.name.contains(query, true) } ||
                year.toString() == query
    }
}