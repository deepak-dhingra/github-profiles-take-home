package com.example.githubprofile.core.di

import com.example.githubprofile.core.data.remote.GithubApi
import com.example.githubprofile.core.domain.repository.GithubRepository
import com.example.githubprofile.core.domain.repository.GithubRepositoryImpl
import com.example.githubprofile.core.domain.usecase.GetGithubProfileUsecase
import com.example.githubprofile.core.domain.usecase.GetProfileReposUsecase
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class GithubModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson().newBuilder().create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://api.github.com")
            .build()
    }

    @Provides
    @Singleton
    fun provideGithubApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGithubProfileUsecase(githubRepository: GithubRepository): GetGithubProfileUsecase {
        return GetGithubProfileUsecase(githubRepository)
    }

    @Provides
    @Singleton
    fun provideProfileReposUsecase(githubRepository: GithubRepository): GetProfileReposUsecase {
        return GetProfileReposUsecase(githubRepository)
    }

    @Provides
    @Singleton
    fun provideGithuubRepository(api: GithubApi): GithubRepository {
        return GithubRepositoryImpl(api)
    }
}