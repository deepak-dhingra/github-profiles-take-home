package com.example.githubprofile.core.domain.repository

import com.example.githubprofile.core.data.model.GithubProfile
import com.example.githubprofile.core.data.model.ProfileRepo
import com.example.githubprofile.core.data.remote.GithubApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class GithubRepositoryImplTest{
    private val githubApi: GithubApi = mock()
    private val repository = GithubRepositoryImpl(githubApi)

    @Test
    fun testGetProfile() = runTest {
        // Mock the API response
        val userId = "userId"
        val profile = GithubProfile("login", "userId", nodeId = "url", name = "name")
        val response = Response.success(profile)
        whenever(githubApi.getGithubProfile(userId)).thenReturn(response)

        // Call the repository method
        val result = repository.getProfile(userId)

        // Verify the result
        assertEquals(response, result)
    }

    @Test
    fun testGetProfileRepos() = runBlocking {
        // Mock the API response
        val userId = "userId"
        val repos = listOf(
            ProfileRepo(1, "nodeId", "name", "fullName",false),
            ProfileRepo(2, "nodeId", "name", "fullName",false)
        )
        val response = Response.success(repos)
        whenever(githubApi.getProfileRepos(userId)).thenReturn(response)

        // Call the repository method
        val result = repository.getProfileRepos(userId)

        // Verify the result
        assertEquals(response, result)
    }
}