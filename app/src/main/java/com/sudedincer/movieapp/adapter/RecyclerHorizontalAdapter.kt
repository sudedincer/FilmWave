package com.sudedincer.movieapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sudedincer.movieapp.R
import com.sudedincer.movieapp.model.MovieResult

class RecyclerHorizontalAdapter(
    private val movieList: List<MovieResult>,
    private val listener: Listener
) : RecyclerView.Adapter<RecyclerHorizontalAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClick(movie: MovieResult)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieImageView: ImageView = view.findViewById(R.id.posterH)
        private val movieTitle: TextView = view.findViewById(R.id.titleH)
        private val movieYear: TextView = view.findViewById(R.id.dateH)

        fun bind(movie: MovieResult) {
            Glide.with(itemView.context)
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}") // URL'yi uygun şekilde yapılandır
                .into(movieImageView)

            itemView.setOnClickListener { listener.onItemClick(movie) }

            movieTitle.text = movie.title
            movieYear.text = movie.releaseDate
            itemView.setBackgroundColor(Color.parseColor("#1e3d59"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_horizontal_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}