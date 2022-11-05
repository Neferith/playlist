package com.nef.playlist.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nef.playlist.TestCoroutineRule
import com.nef.playlist.data.model.PlaylistEntity
import com.nef.playlist.data.repository.PlaylistRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetPlaylistsUserCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    companion object {
        private val GET_ALL_FLOW_CACHE:List<PlaylistEntity> = mockk()
        private val GET_ALL_FLOW_WITHOUT_CACHE: List<PlaylistEntity> = mockk()
    }

    private val playlistRepository:PlaylistRepository = mockk()

    private val getPlaylistsUserCase: GetPlaylistsUserCase = GetPlaylistsUserCase(playlistRepository)

    @Before
    fun setUp() {

        coEvery { playlistRepository.getPlaylistFromCache() } returns GET_ALL_FLOW_CACHE
        coEvery { playlistRepository.getPlaylistFromNetwork() } returns GET_ALL_FLOW_WITHOUT_CACHE

    }

    @Test
    fun `cache case One emit`() = runTest {
        // When
        val result = getPlaylistsUserCase.invoke(true).count()
        io.mockk.coVerify(exactly = 1) { playlistRepository.getPlaylistFromCache() }
        io.mockk.coVerify(exactly = 0) { playlistRepository.getPlaylistFromNetwork() }
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `cache case Result`() = runTest {
        // When
        val result = getPlaylistsUserCase.invoke(true).first()
        io.mockk.coVerify(exactly = 1) { playlistRepository.getPlaylistFromCache() }
        io.mockk.coVerify(exactly = 0) { playlistRepository.getPlaylistFromNetwork() }
        assertThat(result).isEqualTo(GET_ALL_FLOW_CACHE)
    }

    @Test
    fun `network case One emit`() = runTest {
        // When
        val result = getPlaylistsUserCase.invoke(false).count()
        io.mockk.coVerify(exactly = 0) { playlistRepository.getPlaylistFromCache() }
        io.mockk.coVerify(exactly = 1) { playlistRepository.getPlaylistFromNetwork() }
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `network case Result`() = runTest {
        // When
        val result = getPlaylistsUserCase.invoke(false).first()
        io.mockk.coVerify(exactly = 0) { playlistRepository.getPlaylistFromCache() }
        io.mockk.coVerify(exactly = 1) { playlistRepository.getPlaylistFromNetwork() }
        assertThat(result).isEqualTo(GET_ALL_FLOW_WITHOUT_CACHE)
    }


}