package com.nef.playlist.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nef.playlist.utils.DataState
import com.nef.playlist.TestCoroutineRule
import com.nef.playlist.data.model.PlaylistEntity
import com.nef.playlist.data.repository.PlaylistRepository
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPlaylistsUserCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    companion object {
        private val GET_ALL_FLOW_CACHE:List<PlaylistEntity> = listOf(mockk(), mockk(), mockk())
        private val SUCCESS_CACHE = DataState.Success(GET_ALL_FLOW_CACHE)
        private val GET_ALL_FLOW_WITHOUT_CACHE: List<PlaylistEntity> = listOf(mockk(), mockk(), mockk())
        private val SUCCESS_WITHOUT_CACHE = DataState.Success(GET_ALL_FLOW_WITHOUT_CACHE)
    }

    private val playlistRepository:PlaylistRepository = mockk()

    private val getPlaylistsUserCase: GetPlaylistsUserCase = GetPlaylistsUserCase(playlistRepository)

    @Before
    fun setUp() {

        coEvery { playlistRepository.getPlaylistFromCache() } returns GET_ALL_FLOW_CACHE
        coEvery { playlistRepository.getPlaylistFromNetwork() } returns GET_ALL_FLOW_WITHOUT_CACHE

        coJustRun { playlistRepository.insertInCache(GET_ALL_FLOW_WITHOUT_CACHE) }

    }

    @Test
    fun `cache case One emit`() = runTest {
        // When
        val result = getPlaylistsUserCase.invoke(true).count()
        io.mockk.coVerify(exactly = 1) { playlistRepository.getPlaylistFromCache() }
        io.mockk.coVerify(exactly = 0) { playlistRepository.getPlaylistFromNetwork() }
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `cache case Result`() = runTest {
        // When
        val result = getPlaylistsUserCase.invoke(true).last()
        io.mockk.coVerify(exactly = 1) { playlistRepository.getPlaylistFromCache() }
        io.mockk.coVerify(exactly = 0) { playlistRepository.getPlaylistFromNetwork() }
        assertThat(result).isEqualTo(SUCCESS_CACHE)
    }

    @Test
    fun `network case One emit`() = runTest {
        // When
        val result = getPlaylistsUserCase.invoke(false).count()
        io.mockk.coVerify(exactly = 0) { playlistRepository.getPlaylistFromCache() }
        io.mockk.coVerify(exactly = 1) { playlistRepository.getPlaylistFromNetwork() }
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `network case Result`() = runTest {
        // When
        val result = getPlaylistsUserCase.invoke(false).last()
        io.mockk.coVerify(exactly = 0) { playlistRepository.getPlaylistFromCache() }
        io.mockk.coVerify(exactly = 1) { playlistRepository.getPlaylistFromNetwork() }
        io.mockk.coVerify(exactly = 1) { playlistRepository.insertInCache(any()) }
        assertThat(result).isEqualTo(SUCCESS_WITHOUT_CACHE)
    }




}