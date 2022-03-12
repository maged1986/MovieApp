package com.magednan.movieapp.model

import java.io.Serializable

data class Result (
    val description: String,
    val id: String,
    val image: String,
    val resultType: String,
    val title: String
        ):Serializable