package com.magednan.movieapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.magednan.movieapp.model.HiddenItems
import com.magednan.movieapp.model.Item

@Database(
    entities = [HiddenItems::class],
    version = 1, exportSchema = false
)
abstract class HiddenItemsDB:RoomDatabase() {
    abstract fun getDao(): HiddenItemsDao

}