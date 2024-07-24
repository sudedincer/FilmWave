package com.sudedincer.movieapp.view.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.sudedincer.movieapp.R


class ThirdScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_third_screen, container, false)


        view.findViewById<TextView>(R.id.finish).setOnClickListener{
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
            onboardingFinished()
        }
        return view
    }

    private fun onboardingFinished(){
        val sharedPreferences=requireActivity().getSharedPreferences("onboarding", Context.MODE_PRIVATE)
        val editor =sharedPreferences.edit()
        editor.putBoolean("finished",true)
        editor.apply()

    }

}
