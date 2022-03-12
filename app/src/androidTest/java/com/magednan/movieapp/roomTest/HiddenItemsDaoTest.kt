package com.magednan.movieapp.roomTest

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.magednan.movieapp.database.HiddenItemsDB
import com.magednan.movieapp.database.HiddenItemsDao
import com.magednan.movieapp.model.HiddenItems
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
class HiddenItemsDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("hiddenitemstestDatabase")
    lateinit var database : HiddenItemsDB

    private lateinit var daoTest : HiddenItemsDao

    @Before
    fun setup() {

        hiltRule.inject()
        daoTest = database.getDao()
    }

    @After
    fun teardown() {
        database.close()
    }


    @Test
    fun insertItemTesting() = runBlockingTest {
        val item = HiddenItems(0,"")
        daoTest.upsertHiddenItems(item)

        val list = daoTest.getHiddenItems()
        Truth.assertThat(list).contains(item)

    }

    @Test
    fun deleteItemTesting() = runBlockingTest {
        val item = HiddenItems(0,"")
        daoTest.upsertHiddenItems(item)

        daoTest.deleteHiddenItem()

        val list = daoTest.getHiddenItems()
        Truth.assertThat(list).doesNotContain(item)

    }
    @Test
    fun ContainsItemTesting() = runBlockingTest {
        val item = HiddenItems(0,"")
        daoTest.upsertHiddenItems(item)

        val list = daoTest.getHiddenItems()
        Truth.assertThat(list).contains(item)
    }
}