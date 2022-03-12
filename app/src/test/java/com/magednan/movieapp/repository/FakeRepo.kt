package com.magednan.movieapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.magednan.movieapp.model.*
import com.magednan.movieapp.utils.Resource

class FakeRepo :RepositoryInterface {
    var items= ArrayList<Item>()
    var itemsLiveData= MutableLiveData<List<Item>>()
    val item=Item("","","","","")
    val hiddenItems=HiddenItems(0,"")
    var hiddenItemList= mutableListOf<HiddenItems>()
    var resutls= ArrayList<Result>()
val movieResponse=MovieResponse("",items)
val searchResponse=SearchResponse("","",  resutls,"")
val detailsResponse=DetailsResponse("","","","",""
    ,"","","",false,"","","","","")

    override suspend fun getPopularMovie(api_key: String): Resource<MovieResponse> {
       return Resource.success(movieResponse)
    }

    override suspend fun getMovieDetails(api_key: String, id: String): Resource<DetailsResponse> {
        return Resource.success(detailsResponse)
    }

    override suspend fun searchMovie(
        api_key: String,
        expression: String
    ): Resource<SearchResponse> {
        return Resource.success(searchResponse)

    }

    override suspend fun upsert(item: Item) {
        items.add(item)
        refreshItemsLiveData()
    }

    private fun refreshItemsLiveData() {
       itemsLiveData.value=items
    }

    override fun getItems(): LiveData<List<Item>> {
        return itemsLiveData
    }

    override suspend fun deleteItem(item: Item) {
        deleteItem(item)
    }

    override suspend fun upsertHiddenItems(hiddenItems: HiddenItems) {
       upsertHiddenItems(hiddenItems)
    }

    override fun getHiddenItems(): List<HiddenItems> {
      return hiddenItemList
    }

    override suspend fun deleteHiddenItem() {
       deleteHiddenItem()
    }
}