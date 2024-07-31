package com.sudedincer.movieapp.view

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sudedincer.movieapp.R
import com.sudedincer.movieapp.adapter.RecyclerViewAdapter
import com.sudedincer.movieapp.model.MovieResponse
import com.sudedincer.movieapp.model.MovieResult
import com.sudedincer.movieapp.service.MovieAPI
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeScreen : Fragment(),RecyclerViewAdapter.Listener {

    private val BASE_URL = "https://api.themoviedb.org/3/"
    private var upcomingMovies: List<MovieResult>? = null
    private var nowPlayingMovies: List<MovieResult>? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private lateinit var recyclerViewUpcoming: RecyclerView
    private lateinit var recyclerViewNowPlaying: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)

        recyclerViewUpcoming = view.findViewById(R.id.recycler_view_upcoming)
        recyclerViewNowPlaying = view.findViewById(R.id.recycler_view_now_playing)



        val bottomNavigationView: BottomNavigationView = view.findViewById(R.id.bottom_navigation_fav)

        bottomNavigationView.selectedItemId = R.id.navigation_home
        bottomNavigationView.itemActiveIndicatorColor= ColorStateList.valueOf(Color.parseColor("#adc1d1"))

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_fav -> {
                    findNavController().navigate(R.id.action_homeScreen_to_favoritesScreen)
                    true
                }
                R.id.navigation_watch -> {
                    findNavController().navigate(R.id.action_homeScreen_to_watchlistScreen)
                    true
                }
                R.id.navigation_profile -> {
                    findNavController().navigate(R.id.action_homeScreen_to_profileFragment)
                    true
                }
                else -> false
            }
        }

        recyclerViewUpcoming = view.findViewById(R.id.recycler_view_upcoming)
        val layoutManagerUpcoming = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewUpcoming.layoutManager = layoutManagerUpcoming


        recyclerViewNowPlaying = view.findViewById(R.id.recycler_view_now_playing)
        val layoutManagerNowPlaying = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewNowPlaying.layoutManager = layoutManagerNowPlaying

        val itemDecoration = SpaceItemDecoration(16)
        recyclerViewUpcoming.addItemDecoration(itemDecoration)
        recyclerViewNowPlaying.addItemDecoration(itemDecoration)


        loadUpcomingData()
        loadNowPlayingData()

        return view
    }

    private fun loadUpcomingData() {
        val retrofitHelper = com.sudedincer.movieapp.Repository.Retrofit()

        retrofitHelper.loadMovie { upcomingMovies ->
            if (upcomingMovies != null) {
                recyclerViewAdapter = RecyclerViewAdapter(upcomingMovies, this@HomeScreen)
                recyclerViewUpcoming.adapter = recyclerViewAdapter
            } else {
                Toast.makeText(requireContext(), "Failed to load upcoming movies.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadNowPlayingData() {
        val retrofitHelper = com.sudedincer.movieapp.Repository.Retrofit()

        retrofitHelper.loadMovie { nowPlayingMovies ->
            if (nowPlayingMovies != null) {
                recyclerViewAdapter = RecyclerViewAdapter(nowPlayingMovies, this@HomeScreen)
                recyclerViewNowPlaying.adapter = recyclerViewAdapter
            } else {
                Toast.makeText(requireContext(), "Failed to load now playing movies.", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onItemClick(movie: MovieResult) {
        val bundle = Bundle().apply {
            putString("id", "${movie.id}")
        }

        findNavController().navigate(R.id.action_homeScreen_to_detailsScreen, bundle)
    }
    }

    class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            outRect.left = space
            outRect.right = space
            outRect.top = space
            outRect.bottom = space
        }
    }

