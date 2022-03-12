package com.magednan.movieapp.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.magednan.movieapp.MainCoroutineRule
import com.magednan.movieapp.getOrAwaitValueTest
import com.magednan.movieapp.repository.FakeRepo
import com.magednan.movieapp.utils.Status
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import com.magednan.movieapp.model.HiddenItems
import com.magednan.movieapp.model.Item
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewModelTest : TestCase() {
    private lateinit var viewModel:ViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    @Before
    fun setup(){
        viewModel= ViewModel(FakeRepo())
    }

    @Test
    fun getPopularMovieSuccess() {
        runBlocking {
            viewModel.getPopularMovie("nkkj")

            val value = viewModel.movie.getOrAwaitValueTest()
            assertThat(value.status).isEqualTo(Status.SUCCESS)
        }
    }
    @Test
    fun getPopularMovieFail() {
        runBlocking {
            viewModel.getPopularMovie("")

            val value = viewModel.movie.getOrAwaitValueTest()
            assertThat(value.status).isEqualTo(Status.ERROR)
        }
    }
    @Test
    fun searchMovieSuccess() {
        runBlocking {
            viewModel.searchMovie("nkkj","kjjk")

            val value = viewModel.search.getOrAwaitValueTest()
            assertThat(value.status).isEqualTo(Status.SUCCESS)
        }
    }
    @Test
    fun searchMovieFail() {
        runBlocking {
            viewModel.searchMovie("","")

            val value = viewModel.search.getOrAwaitValueTest()
            assertThat(value.status).isEqualTo(Status.ERROR)
        }
    }
    @Test
    fun upsertItemSuccess() {
        runBlocking {
            val item=Item("","","","","")
            viewModel.upsert(item)

            val value = viewModel.getItems().getOrAwaitValueTest()
            assertThat(value).contains(item)
        }
    }
    @Test
    fun upsertItemFail() {
        runBlocking {
            val item=Item("","","","","")
            viewModel.upsert(item)
            val value = viewModel.getItems().getOrAwaitValueTest()
            assertThat(value).doesNotContain(item)
        }
    }
    @Test
    fun upsertHiddenItemSuccess() {
        runBlocking {
            val item=HiddenItems(0,"")
            viewModel.upsertHiddenItems(item)

            val value = viewModel.getHiddenItems()
            assertThat(value).contains(item)
        }
    }
    @Test
    fun upsertHiddenItemFail() {
        runBlocking {
            val item=HiddenItems(0,"")
            viewModel.upsertHiddenItems(item)
            val value = viewModel.getHiddenItems()
            assertThat(value).doesNotContain(item)
        }
    }
}