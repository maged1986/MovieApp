package com.magednan.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.magednan.movieapp.model.HiddenItems
import com.magednan.movieapp.model.Item
import com.magednan.movieapp.model.MovieResponse
import com.magednan.movieapp.model.SearchResponse
import com.magednan.movieapp.repository.Repository
import com.magednan.movieapp.repository.RepositoryInterface
import com.magednan.movieapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    val repository: RepositoryInterface
):ViewModel() {

    //rate live data for converting the currency
    private val _movie = MutableLiveData<Resource<MovieResponse>>()
    val movie: LiveData<Resource<MovieResponse>>
        get() = _movie
    //allRates live data for other currency rates
    private val _search = MutableLiveData<Resource<SearchResponse>>()
    val search: LiveData<Resource<SearchResponse>>
        get() = _search

    //to provide movies
    suspend fun getPopularMovie(apiKey: String
    ) = viewModelScope.launch {
        if (apiKey.isEmpty()) {
            _movie.postValue(Resource.error("There is an error", null))
        }else{
            _movie.value = repository.getPopularMovie(apiKey)
        }
    }

    //to search for a movie  movies
    suspend fun searchMovie(apiKey: String,expression: String
    ) = viewModelScope.launch {
        if (apiKey.isEmpty()||expression.isEmpty()) {
            _search.postValue(Resource.error("There is an error", null))
        }else{
            _search.value = repository.searchMovie(apiKey,expression) as Resource<SearchResponse>
        }
    }

    //to save movies in room db
    suspend fun upsert(item: Item) {
        repository.upsert(item)
    }

    //to get movies from room db
    fun getItems(): LiveData<List<Item>> {
        return repository.getItems()
    }

    //to delete movies from room db
    suspend fun deleteItem(item: Item) {
        return repository.deleteItem(item)
    }

    //to save hidden movies in room db
    suspend fun upsertHiddenItems(item: HiddenItems) {
        repository.upsertHiddenItems(item)
    }

    //to get hidden movies from room db
    fun getHiddenItems(): List<HiddenItems> {
       return  repository.getHiddenItems()
    }

    //to delete all hidden movies from room db
    suspend fun deleteHiddenItem() {
        return repository.deleteHiddenItem()
    }

}