package com.sudedincer.movieapp.service

import com.sudedincer.movieapp.model.MovieData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailAPI {
    @GET("movie/{id}?api_key=63cfcffb883d9a5c4c7dbcb93f049d6c")
    fun getMovieDetails(@Path("id") id: String): Call<MovieData>
}
