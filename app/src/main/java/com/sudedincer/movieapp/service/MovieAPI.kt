package com.sudedincer.movieapp.service

import com.sudedincer.movieapp.model.MovieData
import com.sudedincer.movieapp.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieAPI {

    @GET("movie/now_playing?api_key=63cfcffb883d9a5c4c7dbcb93f049d6c")
    fun getNowPlayingMovies(): Call<MovieResponse>

    @GET("movie/upcoming?api_key=63cfcffb883d9a5c4c7dbcb93f049d6c")
    fun getUpcomingMovies(): Call<MovieResponse>


    @GET("movie/{id}?api_key=63cfcffb883d9a5c4c7dbcb93f049d6c")
    fun getMovieDetails(@Path("id") id: String): Call<MovieData>
}