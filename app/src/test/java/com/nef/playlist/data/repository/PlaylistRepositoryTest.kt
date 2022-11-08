package com.nef.playlist.data.repository

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.nef.playlist.TestCoroutineRule
import com.nef.playlist.data.dao.PlaylistCacheEntity
import com.nef.playlist.data.dao.PlaylistCacheMapper
import com.nef.playlist.data.dao.PlaylistDao
import com.nef.playlist.data.network.api.PlaylistApi
import com.nef.playlist.data.network.api.PlaylistMapper
import com.nef.playlist.data.network.api.PlaylistResponseItem
import com.nef.playlist.data.model.PlaylistEntity
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlaylistRepositoryTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    companion object {
        private val GET_ALL_DAO: List<PlaylistCacheEntity> = mockk()
        private val GET_ALL_API: List<PlaylistResponseItem> = mockk()
        private val GET_ALL_FROM_CACHE: List<PlaylistEntity> = mockk()
        private val GET_ALL_FROM_API: List<PlaylistEntity> = listOf(mockk(), mockk(), mockk())
        private val INSERT_PLAYLIST: PlaylistCacheEntity = mockk()
    }

    private val projectDao: PlaylistDao = mockk()
    private val cacheMapper: PlaylistCacheMapper = mockk()
    private val playlistApi: PlaylistApi = mockk()
    private val playlistMapper: PlaylistMapper = mockk()

    private val playlistRepository = PlaylistRepository(
        projectDao,
        cacheMapper,
        playlistApi,
        playlistMapper
    )

    @Before
    fun setUp() {
        coEvery { projectDao.getAll() } returns GET_ALL_DAO
        coEvery { playlistApi.getAll() } returns GET_ALL_API
        coEvery { cacheMapper.mapFromEntityList(GET_ALL_DAO) } returns GET_ALL_FROM_CACHE
        coEvery { cacheMapper.mapToEntity(any()) } returns INSERT_PLAYLIST
        coEvery { playlistMapper.mapFromEntityList(GET_ALL_API) } returns GET_ALL_FROM_API
        coJustRun {projectDao.insert(any())}
    }

    @Test
    fun `cache case`() = testCoroutineRule.runTest {
        // When
        val result = playlistRepository.getPlaylistFromCache()

        // Then
        assertThat(result).isEqualTo(GET_ALL_FROM_CACHE)
        io.mockk.coVerify(exactly = 1) { projectDao.getAll() }
        io.mockk.coVerify(exactly = 1) { cacheMapper.mapFromEntityList(GET_ALL_DAO)}
        io.mockk.coVerify(exactly = 0) { playlistApi.getAll() }
        io.mockk.coVerify(exactly = 0) { playlistMapper.mapFromEntityList(GET_ALL_API) }
        io.mockk.coVerify(exactly = 0) { playlistMapper.mapToEntity(any()) }
        confirmVerified(projectDao)
    }


    @Test
    fun `api case`()  = testCoroutineRule.runTest {
        // When
        val result = playlistRepository.getPlaylistFromNetwork()

        // Then
        assertThat(result).isEqualTo(GET_ALL_FROM_API)
        io.mockk.coVerify(exactly = 0) { projectDao.getAll() }
        io.mockk.coVerify(exactly = 0) { cacheMapper.mapFromEntityList(GET_ALL_DAO)}
        io.mockk.coVerify(exactly = 1) { playlistApi.getAll() }
        io.mockk.coVerify(exactly = 1) { playlistMapper.mapFromEntityList(GET_ALL_API) }

        confirmVerified(playlistApi)

    }


    @Test
    fun `insert data case`()  = testCoroutineRule.runTest {
        // When
       playlistRepository.insertInCache(GET_ALL_FROM_API)

        // Then
        io.mockk.coVerify(exactly = 3) { projectDao.insert(any()) }
        io.mockk.coVerify(exactly = 3) { cacheMapper.mapToEntity(any()) }
        confirmVerified(projectDao)

    }
}