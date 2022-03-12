package com.magednan.movieapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.magednan.movieapp.model.HiddenItems
import com.magednan.movieapp.model.Item
@Dao
interface HiddenItemsDao {
    //update &  insert items in db
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertHiddenItems(hiddenItems: HiddenItems)

    //get all items in db
    @Query("SELECT * FROM hidden_items")
    fun getHiddenItems(): List<HiddenItems>    //delete item in db
    @Query("DELETE FROM hidden_items")
    suspend fun deleteHiddenItem()
}