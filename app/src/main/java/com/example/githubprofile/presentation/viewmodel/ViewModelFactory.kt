package com.example.githubprofile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubprofile.core.domain.usecase.GetGithubProfileUsecase
import com.example.githubprofile.core.domain.usecase.GetProfileReposUsecase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val githubProfileUsecase: GetGithubProfileUsecase,
    private val profileReposUsecase: GetProfileReposUsecase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GithubViewModel(profileReposUsecase, githubProfileUsecase) as T
    }
}