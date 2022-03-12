package com.magednan.movieapp.model

data class DetailsResponse(
    val contentRating: String,
    val fullTitle: String,
    val id: String,
    val imDbRating: String,
    val image: String,
    val originalTitle: String,
    val plot: String,
    val plotLocal: String,
    val plotLocalIsRtl: Boolean,
    val posters: Any,
    val ratings: Any,
    val releaseDate: String,
    val title: String,
    val year: String
)
