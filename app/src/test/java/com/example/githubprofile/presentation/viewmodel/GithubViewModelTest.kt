package com.example.githubprofile.presentation.viewmodel

import com.example.githubprofile.MainCoroutineScopeRule
import com.example.githubprofile.core.common.Result
import com.example.githubprofile.core.data.model.GithubProfile
import com.example.githubprofile.core.data.model.ProfileRepo
import com.example.githubprofile.core.data.model.ResponseStates
import com.example.githubprofile.core.domain.usecase.GetGithubProfileUsecase
import com.example.githubprofile.core.domain.usecase.GetProfileReposUsecase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class GithubViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainCoroutineScopeRule()

    private lateinit var viewModel: GithubViewModel

    @Mock
    private lateinit var mockProfileReposUsecase: GetProfileReposUsecase

    @Mock
    private lateinit var mockGetProfileUsecase: GetGithubProfileUsecase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        viewModel = GithubViewModel(mockProfileReposUsecase, mockGetProfileUsecase)
    }

    @Test
    fun testGetGithubProfileSuccess() = runTest {
        // Mock the successful profile response
        val userId = "userId"
        val profile = GithubProfile("login", "userId", avatarUrl = "avatarUrl", url = "url", name = "name")
        Mockito.`when`(mockGetProfileUsecase(userId)).thenReturn(flowOf(Result.Success(profile)))

        val repos = emptyList<ProfileRepo>()
        Mockito.`when`(mockProfileReposUsecase(userId)).thenReturn(flowOf(Result.Success(repos)))

        viewModel.getGithubProfile(userId)

        // Verify the profile state flow
        val profileState = viewModel.profileStateFlow.first()
        assertEquals(ResponseStates.Success(profile), profileState)

        // Verify the profile repos state flow
        val profileReposState = viewModel.profileReposStateFlow.first()
        assertEquals(ResponseStates.Success<List<ProfileRepo>>(emptyList()), profileReposState)
    }

    @Test
    fun testGetGithubProfileError() = runTest {
        // Mock the error profile response
        val userId = "userId"
        val error = IllegalStateException()
        Mockito.`when`(mockGetProfileUsecase(userId)).thenReturn(flowOf(Result.Error<GithubProfile>(error)))

        // Call the view model method
        viewModel.getGithubProfile(userId)

        // Verify the profile state flow
        val profileState = viewModel.profileStateFlow.first()
        assertEquals(ResponseStates.Error<GithubProfile>(error), profileState)
    }

    @Test
    fun testGetProfileReposSuccess() = runTest {
        // Mock the successful profile repos response
        val userId = "userId"
        val repos = listOf(
            ProfileRepo(1, "nodeId", "name", "fullName",false),
            ProfileRepo(2, "nodeId", "name", "fullName",false)
        )
        Mockito.`when`(mockProfileReposUsecase(userId)).thenReturn(flowOf(Result.Success(repos)))

        // Call the view model method
        viewModel.getProfileRepos(userId)

        // Verify the profile repos state flow
        val profileReposState = viewModel.profileReposStateFlow.first()
        assertEquals(ResponseStates.Success(repos), profileReposState)
    }

    @Test
    fun testGetProfileReposError() = runTest {
        // Mock the error profile repos response
        val userId = "userId"
        val error = IllegalStateException()
        Mockito.`when`(mockProfileReposUsecase(userId)).thenReturn(flowOf(Result.Error<List<ProfileRepo>>(error)))

        // Call the view model method
        viewModel.getProfileRepos(userId)

        // Verify the profile repos state flow
        val profileReposState = viewModel.profileReposStateFlow.first()
        assertEquals(ResponseStates.Error<List<ProfileRepo>>(error), profileReposState)
    }
}