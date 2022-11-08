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
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainViewModelTest {

    companion object {
        private val GET_ALL_FLOW_CACHE:List<PlaylistEntity> = listOf(mockk(), mockk(), mockk())
        private val SUCCESS_CACHE = DataState.Success(GET_ALL_FLOW_CACHE)
        private val GET_ALL_FLOW_WITHOUT_CACHE: List<PlaylistEntity> = listOf(mockk(), mockk(), mockk())
        private val SUCCESS_WITHOUT_CACHE = DataState.Success(GET_ALL_FLOW_WITHOUT_CACHE)
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