package com.example.githubprofile.core.di

import androidx.lifecycle.ViewModelProvider
import com.example.githubprofile.core.domain.usecase.GetGithubProfileUsecase
import com.example.githubprofile.core.domain.usecase.GetProfileReposUsecase
import com.example.githubprofile.presentation.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideViewModelFactory(profile: GetGithubProfileUsecase, reposUsecase: GetProfileReposUsecase) =
            ViewModelFactory(profile, reposUsecase)
    }
}