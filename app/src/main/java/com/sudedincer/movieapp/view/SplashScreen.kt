
package com.sudedincer.movieapp.view

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.sudedincer.movieapp.R


class SplashScreen : Fragment() {
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth= Firebase.auth

        Handler(Looper.getMainLooper()).postDelayed({
            if (auth.currentUser != null) {
                findNavController().navigate(R.id.action_splashScreen_to_homeScreen)
            } else {
                if (onboardingFinished()) {
                    findNavController().navigate(R.id.action_splashScreen_to_loginFragment)
                } else {
                    findNavController().navigate(R.id.action_splashScreen_to_viewPagerFragment)
                }
            }
        }, 3000)

        return inflater.inflate(R.layout.fragment_splash_screen,container,false)
    }


    private fun onboardingFinished(): Boolean {
        val sharedPreferences = requireActivity().getSharedPreferences("onboarding", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("finished", false)
    }


}