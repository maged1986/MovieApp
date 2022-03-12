package com.magednan.movieapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.magednan.movieapp.model.Item

@Dao
interface MovieDao {
    //update &  insert items in db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: Item)

    //get all items in db
   @Query("SELECT * FROM items")
    fun getItems(): LiveData<List<Item>>
    //delete item in db
    @Delete
    suspend fun deleteItem(item: Item)
}