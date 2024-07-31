package com.sudedincer.movieapp.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val dates: MovieDates,
    @SerializedName("page") val page: Int,
    val results: List<MovieResult>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)