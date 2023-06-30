package com.example.githubprofile.core.domain.usecase

import com.example.githubprofile.core.common.Result
import com.example.githubprofile.core.data.model.ProfileRepo
import com.example.githubprofile.core.domain.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProfileReposUsecase(private val repository: GithubRepository) {

    operator fun invoke(userId: String): Flow<Result<List<ProfileRepo>>> = flow {
        val response = repository.getProfileRepos(userId)
        if(response.isSuccessful) {
            response.body()?.let {
                emit(Result.Success(it))
            }?: emit(Result.Error(IllegalStateException()))
        } else {
            emit(Result.Error(IllegalStateException()))
        }
    }
}