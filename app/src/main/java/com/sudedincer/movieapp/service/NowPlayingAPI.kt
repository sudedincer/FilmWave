package com.sudedincer.movieapp.service

import com.sudedincer.movieapp.model.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface NowPlayingAPI {

    @GET("movie/now_playing?api_key=63cfcffb883d9a5c4c7dbcb93f049d6c")
    fun getNowPlayingMovies(): Call<MovieResponse>

}