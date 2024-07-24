package com.sudedincer.movieapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCountry (@SerializedName("iso_3166_1")val iso_3166_1: String,
                              @SerializedName("name")val name: String)