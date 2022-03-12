package com.magednan.movieapp.model

data class SearchResponse (
    val errorMessage: String,
    val expression: String,
    val results: ArrayList<Result>,
    val searchType: String
        )
