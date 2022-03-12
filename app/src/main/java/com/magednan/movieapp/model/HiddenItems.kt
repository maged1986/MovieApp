package com.magednan.movieapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hidden_items")
data class HiddenItems(
    @PrimaryKey(autoGenerate = true)
    val id:Int=0,
    val movie_id:String
)
