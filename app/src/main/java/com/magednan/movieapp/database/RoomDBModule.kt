package com.magednan.movieapp.database

import android.app.Application
import androidx.room.Room
import com.magednan.movieapp.model.HiddenItems
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {
    //to provideDB and inject room db in app
    @Provides
    @Singleton
    fun provideMoviesDB(application: Application?) =
        Room.databaseBuilder(application!!, MovieDB::class.java, "MovieDB")
            .allowMainThreadQueries()
            .build()

    //to provideDB and inject room dao in app
    @Provides
    @Singleton
    fun provideMoviesDao(db: MovieDB) = db.getMovieDao() //to provideDB and inject room db in app

    //to provideDB and inject room db in app
    @Provides
    @Singleton
    fun provideHiddenItemsDB(application: Application?) =
        Room.databaseBuilder(application!!, HiddenItemsDB::class.java, "HiddenItemsDB")
            .allowMainThreadQueries()
            .build()

    //to provideDB and inject room dao in app
    @Provides
    @Singleton
    fun provideHiddenItemsDao(db: HiddenItemsDB) = db.getDao()
}