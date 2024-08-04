package com.sudedincer.movieapp.Repository

import com.google.android.gms.common.api.Response
import com.sudedincer.movieapp.adapter.RecyclerViewAdapter
import com.sudedincer.movieapp.model.MovieData
import com.sudedincer.movieapp.model.MovieResponse
import com.sudedincer.movieapp.model.MovieResult
import com.sudedincer.movieapp.service.MovieAPI
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Retrofit() {
    private val baseUrl="https://api.themoviedb.org/3/"

    fun loadNowPlayingMovie(callback: (List<MovieResult>?) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MovieAPI::class.java) // Retrofit ile servisi bağlama
        val call = service.getNowPlayingMovies()

        call.enqueue(object : retrofit2.Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                t.printStackTrace()
                callback(null) // Hata durumunda geri döndürme
            }

            override fun onResponse(
                call: Call<MovieResponse>,
                response: retrofit2.Response<MovieResponse>,
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { movieResponse ->
                        callback(movieResponse.results) // Başarılı olduğunda verileri geri döndürme
                    }
                } else {
                    callback(null) // Yanıt başarılı değilse null geri döndür
                }
            }
        })
    }

    fun loadUpcomingMovie(callback: (List<MovieResult>?) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MovieAPI::class.java) // Retrofit ile servisi bağlama
        val call = service.getUpcomingMovies()

        call.enqueue(object : retrofit2.Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                t.printStackTrace()
                callback(null) // Hata durumunda geri döndürme
            }

            override fun onResponse(
                call: Call<MovieResponse>,
                response: retrofit2.Response<MovieResponse>,
            ) {
                if (response.isSuccessful) {
                    response.body()?.let { movieResponse ->
                        callback(movieResponse.results) // Başarılı olduğunda verileri geri döndürme
                    }
                } else {
                    callback(null) // Yanıt başarılı değilse null geri döndür
                }
            }
        })
    }


    fun loadMovieDetails(movieId: String, callback: (MovieData?) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MovieAPI::class.java)
        val call = service.getMovieDetails(movieId)

        call.enqueue(object : retrofit2.Callback<MovieData> {
            override fun onResponse(
                call: Call<MovieData>,
                response: retrofit2.Response<MovieData>
            ) {
                if (response.isSuccessful) {
                    callback(response.body()) // Başarılı olursa veriyi döndür
                } else {
                    callback(null) // Başarısız olursa null döndür
                }
            }

            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                t.printStackTrace()
                callback(null) // Hata durumunda null döndür
            }
        })
    }
}