package com.sudedincer.movieapp.view.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.sudedincer.movieapp.R

class SecondScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_second_screen, container, false)

        val viewPager=activity?.findViewById<ViewPager2>(R.id.viewPager)

        view.findViewById<TextView>(R.id.finish).setOnClickListener{
            viewPager?.currentItem= 2
        }
        return view
    }
    }


