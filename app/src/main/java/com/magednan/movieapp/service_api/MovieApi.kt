package com.magednan.movieapp.service_api

import com.magednan.movieapp.model.DetailsResponse
import com.magednan.movieapp.model.MovieResponse
import com.magednan.movieapp.model.SearchResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    //to getPopularMovie
    @GET("Top250Movies/{apiKey}")
    suspend fun getPopularMovie(
        @Path("apiKey") api_key:String
    ): Response<MovieResponse>

  //to searchMovie by title
    @GET("Search/{apiKey}/{expression}")
    suspend fun searchMovie(
        @Path("apiKey") api_key:String,
        @Path("expression") expression: String
    ): Response<SearchResponse>
}