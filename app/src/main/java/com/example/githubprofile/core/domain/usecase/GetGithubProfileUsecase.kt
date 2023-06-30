package com.example.githubprofile.core.domain.usecase

import com.example.githubprofile.core.data.model.GithubProfile
import com.example.githubprofile.core.domain.repository.GithubRepository
import com.example.githubprofile.core.common.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGithubProfileUsecase @Inject constructor(private val repository: GithubRepository) {

    operator fun invoke(userId: String): Flow<Result<GithubProfile>> = flow {
        val response = repository.getProfile(userId)
        if(response.isSuccessful) {
            response.body()?.let {
                emit(Result.Success(it))
            }?: emit(Result.Error(IllegalStateException()))
        } else {
            emit(Result.Error(IllegalStateException()))
        }
    }
}