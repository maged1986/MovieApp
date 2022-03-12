package com.magednan.movieapp.repository

import androidx.lifecycle.LiveData
import com.magednan.movieapp.database.HiddenItemsDao
import com.magednan.movieapp.database.MovieDao
import com.magednan.movieapp.model.*
import com.magednan.movieapp.service_api.MovieApi
import com.magednan.movieapp.utils.Resource
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    val api: MovieApi,
    val movieDao: MovieDao,
    val hiddenItemsDao: HiddenItemsDao
) : RepositoryInterface {

    //to get movies from api and check the case of response
    override suspend fun getPopularMovie(api_key: String): Resource<MovieResponse> {
        return try {
            val response = api.getPopularMovie(api_key)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No data!", null)
        }
    }


    //to get movies from api and check the case of response
    override suspend fun searchMovie(
        api_key: String,
        expression: String
    ): Resource<SearchResponse> {
        return try {
            val response = api.searchMovie(api_key, expression)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }

    } catch (e: Exception)
    {
        Resource.error("No data!", null)
    }
}
    //to save movies in room db
    override suspend fun upsert(item: Item) {
        movieDao.upsert(item)
    }

    //to get movies from room db
    override fun getItems(): LiveData<List<Item>> {
        return movieDao.getItems()
    }
    //to delete movies from room db
    override suspend fun deleteItem(item: Item) {
         movieDao.deleteItem(item)
    }

    //to save hidden movies in room db
    override suspend fun upsertHiddenItems(hiddenItems: HiddenItems) {
        hiddenItemsDao.upsertHiddenItems(hiddenItems)
    }

    //to get hidden movies from room db
    override fun getHiddenItems(): List<HiddenItems> {
        return hiddenItemsDao.getHiddenItems()
    }

    //to delete all hidden movies from room db
    override suspend fun deleteHiddenItem() {
        hiddenItemsDao.deleteHiddenItem()
    }
}