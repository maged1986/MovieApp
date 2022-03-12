package com.magednan.movieapp.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.action.ViewActions
import com.magednan.movieapp.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class SearchFragmentTest : TestCase(){
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testOnBackPressed() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<SearchFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        ViewActions.pressBack()
        Mockito.verify(navController).popBackStack()
    }

}