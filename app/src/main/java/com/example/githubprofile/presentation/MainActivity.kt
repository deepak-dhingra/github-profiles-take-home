package com.example.githubprofile.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.githubprofile.R
import com.example.githubprofile.presentation.fragment.GithubProfileFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GithubProfileFragment.newInstance()).commit()
    }
}