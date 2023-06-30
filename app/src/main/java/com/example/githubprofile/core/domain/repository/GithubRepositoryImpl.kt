package com.example.githubprofile.core.domain.repository

import com.example.githubprofile.core.data.model.GithubProfile
import com.example.githubprofile.core.data.model.ProfileRepo
import com.example.githubprofile.core.data.remote.GithubApi
import retrofit2.Response
import javax.inject.Inject

class GithubRepositoryImpl @Inject constructor(private val githubApi: GithubApi): GithubRepository {

    override suspend fun getProfile(userId: String): Response<GithubProfile> {
        return githubApi.getGithubProfile(userId)
    }

    override suspend fun getProfileRepos(userId: String): Response<List<ProfileRepo> >{
        return githubApi.getProfileRepos(userId)
    }
}