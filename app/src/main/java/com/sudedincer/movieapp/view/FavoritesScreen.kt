package com.sudedincer.movieapp.view

import android.content.ContentValues.TAG
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.sudedincer.movieapp.R
import com.sudedincer.movieapp.adapter.RecyclerHorizontalAdapter
import com.sudedincer.movieapp.model.MovieData
import com.sudedincer.movieapp.model.MovieResult
import com.sudedincer.movieapp.service.MovieAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


    class FavoritesScreen : Fragment() {

        private lateinit var recyclerView: RecyclerView
        private var movieAdapter: RecyclerHorizontalAdapter? = null


        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.fragment_favorites_screen, container, false)

            recyclerView = view.findViewById(R.id.recyclerFav)
            val layoutManagerUpcoming = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            recyclerView.layoutManager = layoutManagerUpcoming


            val bottomNavigationView: BottomNavigationView = view.findViewById(R.id.bottom_navigation_fav)

            bottomNavigationView.selectedItemId = R.id.navigation_fav
            bottomNavigationView.itemActiveIndicatorColor = ColorStateList.valueOf(Color.parseColor("#adc1d1"))

            bottomNavigationView.setOnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.navigation_home -> {
                        findNavController().navigate(R.id.action_favoritesScreen_to_homeScreen)
                        true
                    }
                    R.id.navigation_watch -> {
                        findNavController().navigate(R.id.action_favoritesScreen_to_watchlistScreen)
                        true
                    }
                    R.id.navigation_profile -> {
                        findNavController().navigate(R.id.action_favoritesScreen_to_profileFragment)
                        true
                    }
                    R.id.navigation_fav -> true
                    else -> false
                }
            }

            fetchData()


            return view
        }

        private fun fetchData() {
            val auth = FirebaseAuth.getInstance()
            val firestore = FirebaseFirestore.getInstance()
            val user = auth.currentUser

            if (user != null) {
                firestore.collection("Favorites")
                    .whereEqualTo("email", user.email)
                    .get()
                    .addOnSuccessListener { result ->
                        val movieList = mutableListOf<HashMap<String, Any>>()
                        for (document in result) {
                            val movieData = document.data as HashMap<String, Any>
                            movieList.add(movieData)
                        }

                        movieAdapter = RecyclerHorizontalAdapter(
                            movieList,
                            object : RecyclerHorizontalAdapter.Listener {
                                override fun onItemClick(movie: HashMap<String, Any>) {
                                    val movieId = movie["id"] as? String
                                    if (movieId != null) {
                                        val bundle = Bundle().apply {
                                            putString("id", movieId)
                                        }
                                        findNavController().navigate(R.id.action_favoritesScreen_to_detailsScreen, bundle)
                                    } else {
                                        Toast.makeText(
                                            requireContext(),
                                            "Movie ID not found.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        )
                        recyclerView.adapter = movieAdapter
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(
                            requireContext(),
                            "Could not fetch data: ${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }

    }




