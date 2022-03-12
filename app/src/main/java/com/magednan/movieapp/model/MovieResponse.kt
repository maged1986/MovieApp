package com.magednan.movieapp.model

data class MovieResponse(
    val errorMessage: String,
    val items: List<Item>
)
