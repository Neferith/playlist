package com.nef.playlist.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import assertk.assertThat
import assertk.assertions.isEqualTo

import com.nef.playlist.TestCoroutineRule
import com.nef.playlist.data.model.PlaylistEntity
import com.nef.playlist.domain.GetPlaylistsUserCase
import com.nef.playlist.observeForTesting
import com.nef.playlist.utils.DataState
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    companion object {
        private val SUCCESS = DataState.Success<List<PlaylistEntity>>(mockk())
        private val SUCCESS_WITHOUT_CACHE_F = flowOf(
            DataState.Loading,
            SUCCESS)
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val getPlaylistsUserCaseTest: GetPlaylistsUserCase = mockk()


    @Before
    fun setUp() {
        coEvery { getPlaylistsUserCaseTest.invoke(any()) } returns SUCCESS_WITHOUT_CACHE_F
    }

    private val mainViewModel = MainViewModel(
        testCoroutineRule.getTestCoroutineDispatcherProvider(),
        getPlaylistsUserCaseTest
    )

    @Test
    fun `initial case`() = testCoroutineRule.runTest {
        // When
        mainViewModel.playlistLiveData.observeForTesting(this) {

            // Then
            assertThat(it.value).isEqualTo(SUCCESS)
        }
    }

    @Test
    fun `nominal case`() = testCoroutineRule.runTest {
        mainViewModel.syncData()
        mainViewModel.playlistLiveData.observeForTesting(this) {

            // Then
            assertThat(it.value).isEqualTo(SUCCESS)
        }
    }
}