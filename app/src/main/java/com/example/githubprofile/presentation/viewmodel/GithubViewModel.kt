package com.example.githubprofile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubprofile.core.common.Result
import com.example.githubprofile.core.data.model.GithubProfile
import com.example.githubprofile.core.data.model.ProfileRepo
import com.example.githubprofile.core.data.model.ResponseStates
import com.example.githubprofile.core.domain.usecase.GetGithubProfileUsecase
import com.example.githubprofile.core.domain.usecase.GetProfileReposUsecase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GithubViewModel @Inject constructor(
    private val profileReposUsecase: GetProfileReposUsecase,
    private val getProfileUsecase: GetGithubProfileUsecase
) : ViewModel() {

    private var _profileStateFlow: MutableStateFlow<ResponseStates<GithubProfile>?> = MutableStateFlow(null)
    val profileStateFlow: StateFlow<ResponseStates<GithubProfile>?> = _profileStateFlow

    private var _profileReposStateFlow: MutableStateFlow<ResponseStates<List<ProfileRepo>>?> = MutableStateFlow(null)
    val profileReposStateFlow: StateFlow<ResponseStates<List<ProfileRepo>>?> = _profileReposStateFlow


    fun getGithubProfile(userId: String) {
        getProfileUsecase(userId).onEach { result ->
            when(result) {
                is Result.Success -> {
                    result.data?.let {
                        _profileStateFlow.value = ResponseStates.Success(result.data)
                        getProfileRepos(userId)
                    }
                }
                is Result.Error -> {
                    _profileStateFlow.value = ResponseStates.Error(result.throwable ?: IllegalStateException())
                }
                is Result.Loading -> {
                    _profileStateFlow.value = ResponseStates.Loading(true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getProfileRepos(userId: String) {
        profileReposUsecase(userId).onEach { result ->
            when(result) {
                is Result.Success -> {
                    result.data?.let {
                        _profileReposStateFlow.value = ResponseStates.Success(result.data)
                    }
                }
                is Result.Error -> {
                    _profileReposStateFlow.value = ResponseStates.Error(result.throwable ?: IllegalStateException())
                }
                is Result.Loading -> {
                    _profileReposStateFlow.value = ResponseStates.Loading(true)
                }
            }
        }.launchIn(viewModelScope)
    }
}