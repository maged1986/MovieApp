package com.magednan.movieapp.roomTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.magednan.movieapp.database.MovieDB
import com.magednan.movieapp.database.MovieDao
import com.magednan.movieapp.getOrAwaitValue
import com.magednan.movieapp.model.Item
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class HistoryDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDatabase")
    lateinit var database : MovieDB

    private lateinit var dao : MovieDao

    @Before
    fun setup() {

        hiltRule.inject()
        dao = database.getMovieDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertItemTesting() = runBlockingTest {
        val item = Item("","","","","")
        dao.upsert(item)

        val list = dao.getItems().getOrAwaitValue()
        assertThat(list).contains(item)

    }

    @Test
    fun deleteItemTesting() = runBlockingTest {
        val item = Item("","","","","")
        dao.upsert(item)
        dao.deleteItem(item)

        val list = dao.getItems().getOrAwaitValue()
        assertThat(list).doesNotContain(item)

    }
    @Test
    fun ContainsItemTesting() = runBlockingTest {
        val item = Item("","","","","")
        dao.upsert(item)

        val list = dao.getItems().getOrAwaitValue()
        assertThat(list).contains(item)
    }
}