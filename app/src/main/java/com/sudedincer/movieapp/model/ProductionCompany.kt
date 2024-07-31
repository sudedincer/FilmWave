package com.sudedincer.movieapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductionCompany (@SerializedName("id") val id: Int,
                              @SerializedName("logo_path") val logo_path: String?,
                              @SerializedName("name") val name: String,
                              @SerializedName("origin_country") val origin_country: String)