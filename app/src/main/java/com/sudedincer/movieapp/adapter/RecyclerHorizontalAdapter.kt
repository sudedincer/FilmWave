package com.sudedincer.movieapp.adapter

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.sudedincer.movieapp.model.MovieResult
import com.sudedincer.movieapp.R
import com.sudedincer.movieapp.Repository.Retrofit

class RecyclerHorizontalAdapter(
    private val movieList: List<HashMap<String, Any>>,
    private val listener: Listener
) : RecyclerView.Adapter<RecyclerHorizontalAdapter.ViewHolder>() {

    interface Listener {
        fun onItemClick(movie: HashMap<String, Any>)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieImageView: ImageView = view.findViewById(R.id.posterH)
        private val movieTitle: TextView = view.findViewById(R.id.titleH)
        private val movieYear: TextView = view.findViewById(R.id.dateH)

        fun bind(movie: HashMap<String, Any>) {
            val id = movie["id"] as? String

            if (id != null) {
                Retrofit().loadMovieDetails(id) { movieData ->
                    movieData?.let {
                        val title = it.title
                        val year = it.releaseDate
                        val posterUrl = "https://image.tmdb.org/t/p/w500${it.posterPath}"

                        Glide.with(itemView.context)
                            .load(posterUrl)
                            .placeholder(ColorDrawable(Color.TRANSPARENT))
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(movieImageView)

                        movieTitle.text = title
                        movieYear.text = year

                        movieTitle.setTextColor(Color.WHITE)
                        movieYear.setTextColor(Color.WHITE)
                        movieImageView.setBackgroundColor(Color.parseColor("#1e3d59"))
                    }
                }
            }

            itemView.setOnClickListener { listener.onItemClick(movie) }
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