package com.magednan.movieapp.repository

import com.magednan.movieapp.database.HiddenItemsDao
import com.magednan.movieapp.database.MovieDao
import com.magednan.movieapp.service_api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    //to provide and inject Repository as RepositoryInterface in app
    @Singleton
    @Provides
    fun injectNormalRepo(api: MovieApi,movieDao: MovieDao,hiddenItemsDao: HiddenItemsDao ) = Repository(api, movieDao, hiddenItemsDao) as RepositoryInterface
}