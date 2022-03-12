package com.magednan.movieapp.repository

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.magednan.movieapp.model.*
import com.magednan.movieapp.utils.Resource
import retrofit2.Response
import retrofit2.http.Path

interface RepositoryInterface {
    suspend fun getPopularMovie(api_key:String): Resource<MovieResponse>
    suspend fun searchMovie(api_key:String, expression: String): Resource<SearchResponse>
    suspend fun upsert(item: Item)
    fun getItems(): LiveData<List<Item>>
    suspend fun deleteItem(item: Item)
    suspend fun upsertHiddenItems(hiddenItems: HiddenItems)
    fun getHiddenItems(): List<HiddenItems>
    suspend fun deleteHiddenItem()
}