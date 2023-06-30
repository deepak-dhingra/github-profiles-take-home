package com.example.githubprofile.core.data.model

import com.google.gson.annotations.SerializedName

data class GithubProfile(
    @SerializedName("login")
    val login: String? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("node_id")
    val nodeId: String? = null,
    @SerializedName("avatar_url")
    val avatarUrl: String? = null,
    @SerializedName("gravatar_id")
    val gravatarId: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("name")
    val name: String? = null
    )
