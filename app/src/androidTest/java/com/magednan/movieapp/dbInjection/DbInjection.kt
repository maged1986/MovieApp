package com.magednan.movieapp.dbInjection

import android.content.Context
import androidx.room.Room
import com.magednan.movieapp.database.HiddenItemsDB
import com.magednan.movieapp.database.MovieDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named


@Module
@InstallIn(SingletonComponent::class)
object DbInjection {
    @Provides
    @Named("movietestDatabase")
    fun injectMovieDBInMemoryRoom(@ApplicationContext context : Context) =
        Room.inMemoryDatabaseBuilder(context,MovieDB::class.java)
            .allowMainThreadQueries()
            .build()
    @Provides
    @Named("hiddenitemstestDatabase")
    fun injectHiddenItemsDBInMemoryRoom(@ApplicationContext context : Context) =
        Room.inMemoryDatabaseBuilder(context,HiddenItemsDB::class.java)
            .allowMainThreadQueries()
            .build()
}