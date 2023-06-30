package com.example.githubprofile.core.data.remote

import com.example.githubprofile.core.data.model.GithubProfile
import com.example.githubprofile.core.data.model.ProfileRepo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("/users/{userId}")
    suspend fun getGithubProfile(@Path("userId") userId: String): Response<GithubProfile>

    @GET("/users/{userId}/repos")
    suspend fun getProfileRepos(@Path("userId") userId: String): Response<List<ProfileRepo>>
}