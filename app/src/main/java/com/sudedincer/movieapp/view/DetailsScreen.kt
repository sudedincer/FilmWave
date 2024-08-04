package com.sudedincer.movieapp.view

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.sudedincer.movieapp.R
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
class DetailsScreen : Fragment() {
    private lateinit var voteAverage: TextView
    private lateinit var genres: TextView
    private lateinit var overview: TextView
    private lateinit var movieImageView: ImageView
    private lateinit var movieTitle: TextView
    private lateinit var movieYear: TextView
    private lateinit var backButton: Button
    private lateinit var favButton: Button
    private lateinit var watchlistButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var storage: FirebaseStorage
    private lateinit var movieId: String
    private var isFavorite: Boolean = false
    private var isWatchlist: Boolean = false
    private lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details_screen, container, false)

        movieId = arguments?.getString("id").toString()

        val contentLayout: LinearLayout = view.findViewById(R.id.contentLayout)

        voteAverage = view.findViewById(R.id.voteAverage)
        genres = view.findViewById(R.id.genre)
        overview = view.findViewById(R.id.overview)
        movieImageView = view.findViewById(R.id.poster)
        movieTitle = view.findViewById(R.id.titleH)
        movieYear = view.findViewById(R.id.releaseDate)
        backButton = view.findViewById(R.id.backButton)
        favButton = view.findViewById(R.id.addFavButton)
        watchlistButton=view.findViewById(R.id.addWatchlistButton)
        progressBar = view.findViewById(R.id.progressBar)

        loadData(contentLayout, progressBar)

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        favButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            favButton.isEnabled = false

            toggleFavorite ()

        }

        checkIfFavorite()

        watchlistButton.setOnClickListener {

            progressBar.visibility = View.VISIBLE
            watchlistButton.isEnabled = false

            toggleWatchlist()
        }

        checkIfWatchlist()

        return view
    }

    private fun toggleFavorite() {
        val user = auth.currentUser

        if (user != null) {
            val moviesCollection = firestore.collection("Favorites")
            val query = moviesCollection
                .whereEqualTo("email", user.email)
                .whereEqualTo("id", movieId)
                .get()

            query.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result != null && result.isEmpty) {
                        addFavorite()
                    } else {
                        removeFavorite(result)

                    }
                } else {
                    Toast.makeText(requireContext(), "Error checking favorites: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.GONE
                favButton.isEnabled = true
            }
        } else {
            Toast.makeText(requireContext(), "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addFavorite() {
        val user = auth.currentUser

        if (user != null) {
            val moviesCollection = firestore.collection("Favorites")

            val data1 = hashMapOf(
                "email" to user.email!!,
                "id" to movieId,
            )

            moviesCollection.add(data1)
                .addOnSuccessListener {
                    if (isAdded) {
                        Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
                        isFavorite = true
                        updateFavoriteButton()
                    }
                }
                .addOnFailureListener { e ->
                    if (isAdded) {
                        Toast.makeText(requireContext(), "Error adding document: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun removeFavorite(result: QuerySnapshot) {
        for (document in result) {
            document.reference.delete()
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Removed from favorites", Toast.LENGTH_SHORT).show()
                    isFavorite = false
                    updateFavoriteButton()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error removing document: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun checkIfFavorite() {
        val user = auth.currentUser

        if (user != null) {
            val query = firestore.collection("Favorites")
                .whereEqualTo("email", user.email)
                .whereEqualTo("id", movieId)
                .get()
            query.addOnSuccessListener { result ->
                isFavorite = !result.isEmpty
                updateFavoriteButton()
            }

        }
    }

    private fun updateFavoriteButton() {
        if (isFavorite) {
            favButton.text = "Remove from Favorites"
        } else {
            favButton.text = "Add to Favorites"
        }
    }

    private fun toggleWatchlist() {
        val user = auth.currentUser

        if (user != null) {
            val moviesCollection = firestore.collection("Watchlist")
            val query = moviesCollection
                .whereEqualTo("email", user.email)
                .whereEqualTo("id", movieId)
                .get()

            query.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result
                    if (result != null && result.isEmpty) {
                        addWatchlist()
                    } else {
                        removeWatchlist(result)
                    }
                } else {
                    Toast.makeText(requireContext(), "Error checking watchlist: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
                progressBar.visibility = View.GONE
                watchlistButton.isEnabled = true
            }
        } else {
            Toast.makeText(requireContext(), "User not authenticated", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addWatchlist() {
        val user = auth.currentUser

        if (user != null) {
            val moviesCollection = firestore.collection("Watchlist")

            val data1 = hashMapOf(
                "email" to user.email!!,
                "id" to movieId,

            )

            moviesCollection.add(data1)
                .addOnSuccessListener {
                    if (isAdded) {
                        Toast.makeText(requireContext(), "Added to watchlist", Toast.LENGTH_SHORT).show()
                        isWatchlist=true
                        updateWatchlistButton()
                    }
                }
                .addOnFailureListener { e ->
                    if (isAdded) {
                        Toast.makeText(requireContext(), "Error adding document: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
    private fun removeWatchlist(result: QuerySnapshot) {
        for (document in result) {
            document.reference.delete()
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Removed from watchlist", Toast.LENGTH_SHORT).show()
                    isWatchlist = false
                    updateWatchlistButton()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(requireContext(), "Error removing document: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun checkIfWatchlist() {
        val user = auth.currentUser

        if (user != null) {
            val query = firestore.collection("Watchlist")
                .whereEqualTo("email", user.email)
                .whereEqualTo("id", movieId)
                .get()

            query.addOnSuccessListener { result ->
                isWatchlist = !result.isEmpty
                updateWatchlistButton()
            }
        }
    }

    private fun updateWatchlistButton() {
        if (isWatchlist) {
            watchlistButton.text = "Remove from Watchlist"
        } else {
            watchlistButton.text = "Add to Watchlist"
        }
    }

    private fun loadData(contentLayout: LinearLayout, progressBar: ProgressBar) {
        val retrofitHelper = com.sudedincer.movieapp.Repository.Retrofit()

        retrofitHelper.loadMovieDetails(movieId) { movie ->
            if (movie != null) {
                Glide.with(requireContext())
                    .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                    .into(movieImageView)

                movieTitle.text = movie.title
                movieYear.text = movie.releaseDate
                voteAverage.text = movie.voteAverage.toString()
                overview.text = movie.overview

                val genreText = movie.genres.joinToString(", ") { it.name }
                genres.text = genreText

                progressBar.visibility = View.GONE
                contentLayout.visibility = View.VISIBLE
            } else {
                Toast.makeText(requireContext(), "Failed to load movie details.", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.GONE
            }
        }
    }
}