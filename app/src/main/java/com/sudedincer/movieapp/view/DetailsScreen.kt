package com.sudedincer.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.sudedincer.movieapp.R
import com.sudedincer.movieapp.model.MovieData
import com.sudedincer.movieapp.model.MovieResponse
import com.sudedincer.movieapp.service.MovieDetailAPI
import com.sudedincer.movieapp.service.UpcomingAPI
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsScreen : Fragment() {
    private lateinit var voteAverage: TextView
    private lateinit var genres: TextView
    private lateinit var overview: TextView
    private lateinit var movieImageView: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieYear: TextView
    private lateinit var backButton: Button
    private val BASE_URL = "https://api.themoviedb.org/3/"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_details_screen, container, false)

        voteAverage = view.findViewById(R.id.voteAverage)
        genres = view.findViewById(R.id.genre)
        overview = view.findViewById(R.id.overview)
        movieImageView = view.findViewById(R.id.poster)
        movieTitle = view.findViewById(R.id.titleH)
        movieYear = view.findViewById(R.id.releaseDate)
        backButton=view.findViewById(R.id.backButton)

        loadData()

        backButton.setOnClickListener {
                findNavController().popBackStack()
        }

        return view
    }

    private fun loadData() {
        val movieId = arguments?.getString("id") ?: return

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MovieDetailAPI::class.java)
        val call = service.getMovieDetails(movieId)

        call.enqueue(object : retrofit2.Callback<MovieData> {

            override fun onResponse(
                call: retrofit2.Call<MovieData>,
                response: retrofit2.Response<MovieData>
            ) {
                if (response.isSuccessful) {
                    val movie = response.body()

                    if (movie != null) {
                        Glide.with(view!!.context)
                            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                            .into(movieImageView)
                    }

                    movieTitle.text = movie!!.title
                    movieYear.text = movie!!.releaseDate
                    voteAverage.text=movie!!.voteAverage.toString()
                    overview.text=movie!!.overview

                    var text: String = ""
                    for (genre in movie.genres) {
                        text=text+genre.name+", "
                    }
                    text=text.dropLast(2)
                    genres.text=text
                }
            }

            override fun onFailure(call: Call<MovieData>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }
    fun addFav(view: View) {

    }
    fun addWatchlist(view: View) {

    }



}