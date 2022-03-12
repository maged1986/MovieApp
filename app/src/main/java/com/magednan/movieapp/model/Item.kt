package com.magednan.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "items")
data class Item(
    val fullTitle: String?,
    @PrimaryKey
    val id: String,
    val imDbRating: String?,
    val image: String?,
    val title: String?
    ):Serializable