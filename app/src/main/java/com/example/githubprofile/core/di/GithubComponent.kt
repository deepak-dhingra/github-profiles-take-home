package com.example.githubprofile.core.di

import com.example.githubprofile.presentation.fragment.GithubProfileFragment
import com.example.githubprofile.presentation.fragment.ProfileDetailsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    GithubModule::class,
    ViewModelModule::class
])
interface GithubComponent {
    fun inject(fragment: GithubProfileFragment)
    fun inject(fragment: ProfileDetailsFragment)
}