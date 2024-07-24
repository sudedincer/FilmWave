package com.sudedincer.movieapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDates(
    @SerializedName("maximum") val maximum: String,
    @SerializedName("minimum") val minimum: String
)