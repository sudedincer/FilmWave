package com.sudedincer.movieapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Genre (
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String)