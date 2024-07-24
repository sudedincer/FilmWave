package com.sudedincer.movieapp.view

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sudedincer.movieapp.R

class FavoritesScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_favorites_screen, container, false)

        val bottomNavigationView: BottomNavigationView = view.findViewById(R.id.bottom_navigation_fav)

        bottomNavigationView.selectedItemId = R.id.navigation_fav
        bottomNavigationView.itemActiveIndicatorColor= ColorStateList.valueOf(Color.parseColor("#adc1d1"))

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
                R.id.navigation_fav -> {
                    true
                }

                else -> false
            }
        }


        return view
    }


}