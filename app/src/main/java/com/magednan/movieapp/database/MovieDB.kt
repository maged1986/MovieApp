package com.magednan.movieapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.magednan.movieapp.model.Item

@Database(
    entities = [Item::class],
    version = 1, exportSchema = false
)

abstract class MovieDB: RoomDatabase() {
    abstract fun getMovieDao(): MovieDao

}