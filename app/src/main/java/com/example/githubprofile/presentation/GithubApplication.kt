package com.example.githubprofile.presentation

import android.app.Application
import com.example.githubprofile.core.di.DaggerGithubComponent
import com.example.githubprofile.core.di.GithubComponent

class GithubApplication : Application() {

    lateinit var component: GithubComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerGithubComponent.create()

    }
}