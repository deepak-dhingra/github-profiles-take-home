package com.example.githubprofile.core.domain.repository

import com.example.githubprofile.core.data.model.GithubProfile
import com.example.githubprofile.core.data.model.ProfileRepo
import retrofit2.Response

interface GithubRepository {

    suspend fun getProfile(userId: String): Response<GithubProfile>

    suspend fun getProfileRepos(userId: String): Response<List<ProfileRepo>>
}