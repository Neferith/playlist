package com.nef.playlist.domain

import com.nef.playlist.TestCoroutineRule
import com.nef.playlist.data.dao.PlaylistCacheEntity
import com.nef.playlist.data.dao.PlaylistCacheMapper
import com.nef.playlist.data.dao.PlaylistDao
import com.nef.playlist.data.network.api.PlaylistApi
import com.nef.playlist.data.network.api.PlaylistMapper
import com.nef.playlist.model.PlaylistEntity
import io.mockk.coEvery
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import assertk.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetPlaylistsUserCaseTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    companion object {
        private val GET_ALL_FLOW_DAO:List<PlaylistCacheEntity> = mockk()
        private val GET_ALL_FLOW: Flow<List<PlaylistEntity>> = flowOf()
    }

    private val projectDao: PlaylistDao = mockk()
    private val cacheMapper: PlaylistCacheMapper = mockk()
    private val playlistApi: PlaylistApi = mockk()
    private val playlistMapper: PlaylistMapper = mockk()

    /*private val getProjectsUseCase = GetPlaylistsUserCase(projectDao, cacheMapper, playlistApi, playlistMapper)

    @Before
    fun setUp() {
        coEvery { projectDao.getAll() } returns GET_ALL_FLOW_DAO
    }

    @Test
    fun verify() = testCoroutineRule.runTest {
        // When
        val result = getProjectsUseCase.invoke()

        // Then
        assertThat(result).isEqualTo(GET_ALL_FLOW)
        io.mockk.coVerify(exactly = 1) { projectDao.getAll() }
        confirmVerified(projectDao)
    }*/

}