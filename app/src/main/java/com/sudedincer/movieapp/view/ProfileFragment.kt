package com.sudedincer.movieapp.view

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignIn.getClient
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sudedincer.movieapp.R


class ProfileFragment : Fragment() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var profileImageView: ImageView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var signOutButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)


        val bottomNavigationView: BottomNavigationView = view.findViewById(R.id.bottom_navigation_profile)

        bottomNavigationView.selectedItemId = R.id.navigation_profile
        bottomNavigationView.itemActiveIndicatorColor= ColorStateList.valueOf(Color.parseColor("#adc1d1"))

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    findNavController().navigate(R.id.action_profileFragment_to_homeScreen)
                    true
                }
                R.id.navigation_watch -> {
                    findNavController().navigate(R.id.action_profileFragment_to_watchlistScreen)
                    true
                }
                R.id.navigation_fav -> {
                    findNavController().navigate(R.id.action_profileFragment_to_favoritesScreen)
                    true
                }
                R.id.navigation_fav -> {
                    true
                }

                else -> false
            }
        }


        profileImageView = view.findViewById(R.id.profileImageView)
        nameTextView = view.findViewById(R.id.nameTextView)
        emailTextView = view.findViewById(R.id.emailTextView)
        signOutButton = view.findViewById(R.id.signOutButton)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = getClient(requireContext(), gso)


        val account = GoogleSignIn.getLastSignedInAccount(requireContext())
        updateUI(account)

        signOutButton.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener {
               findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }
        }

        return view
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            nameTextView.text = account.displayName
            emailTextView.text = account.email
            Glide.with(this)
                .load(account.photoUrl)
                .into(profileImageView)
        }
    }
}