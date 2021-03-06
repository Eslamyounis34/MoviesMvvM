package com.moviesguide.kotlinstudy.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.moviesguide.kotlinstudy.R
import com.moviesguide.kotlinstudy.room.FavouritesMovies
import com.moviesguide.kotlinstudy.viewmodels.MovieDetailsViewModel
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import com.squareup.picasso.Picasso

class Details : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (isNetworkAvailable()){
            setContentView(R.layout.activity_details)
            val intent = intent.getStringExtra("MovieID").toString()
            lateinit var viewModel: MovieDetailsViewModel
            lateinit var backdroprecyclerview: RecyclerView
            lateinit var videosrecyclerview: RecyclerView
            lateinit var genresrecyclerview: RecyclerView
            lateinit var castrecyclerview: RecyclerView
            var movieName: TextView
            var movieRate: TextView
            var movieOverView: TextView
            var movieRunTime: TextView
            var movieRating: CircularProgressBar
            var moviePoster: ImageView
            var favouriteIcon: ImageView
            var noTrailerstx:TextView
            var noCasttx:TextView
            var noPhotostx:TextView

            backdroprecyclerview = findViewById(R.id.backdroprecycler)
            videosrecyclerview = findViewById(R.id.videoslistrecyclerview)
            genresrecyclerview = findViewById(R.id.genresrecyclerview)
            castrecyclerview = findViewById(R.id.castrecyclerview)
            movieName = findViewById(R.id.details_movieName)
            movieRate = findViewById(R.id.movieRateText)
            movieOverView = findViewById(R.id.overviewmovie)
            movieRunTime = findViewById(R.id.movieRunTime)
            movieRating = findViewById(R.id.movieRate)
            moviePoster = findViewById(R.id.posterimage)
            favouriteIcon = findViewById(R.id.favoriteiconview)
            noTrailerstx=findViewById(R.id.notrailers)
            noCasttx=findViewById(R.id.nocast)
            noPhotostx=findViewById(R.id.nophotos)
            var progressBar=findViewById<View>(R.id.details_progress_bar) as ProgressBar



            progressBar.visibility=View.VISIBLE
            var MOVIENAME = ""
            var MOVIEPOSTER = ""
            var MOVIERATE = ""



            viewModel = ViewModelProvider(this).get(MovieDetailsViewModel::class.java)


            var checkREsult = viewModel.checkExists(intent.toInt())
            if (checkREsult == true) {
                favouriteIcon.setImageResource(R.drawable.offwhite_unfav_icon)
            } else {
                favouriteIcon.setImageResource(R.drawable.offwhite_fav_icon)

            }
            viewModel.getMovieImages(intent).observe(this, Observer {
                backdroprecyclerview.apply {
                    if (it.isEmpty()){
                        backdroprecyclerview.visibility=View.GONE
                        noPhotostx.visibility=View.VISIBLE

                    }else{
                        adapter = BackDropRecyclerAdapter(it)

                    }
                }
            })

            viewModel.getMovieVideos(intent).observe(this, Observer {

                videosrecyclerview.apply {
                    if(it.isEmpty())
                    {
                        videosrecyclerview.visibility=View.GONE
                        noTrailerstx.visibility= View.VISIBLE

                    }
                    else{
                        adapter = VideosRecyclerAdapter(it)

                    }

                }
            })

            viewModel.getMoviesData(intent).observe(this, Observer {

                Picasso.get().load("https://image.tmdb.org/t/p/w1280/" + it.poster_path)
                    .into(moviePoster)
                progressBar.visibility=View.GONE

                MOVIENAME = it.title
                MOVIEPOSTER = it.poster_path
                MOVIERATE = it.vote_average

                movieName.setText(it.title)
                movieRate.setText(it.vote_average)
                movieOverView.setText(it.overview)
                movieRunTime.setText(it.runtime + " Min")
                genresrecyclerview.apply {
                    adapter = GenresRecyclerAdapter(it.genres)
                }

                favouriteIcon.setOnClickListener {
                    if (checkREsult == true) {
                        Toast.makeText(
                            this,
                            "This Movie Already Exists In Your Favourites",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else  {
                        val movie =
                            FavouritesMovies(0, intent.toInt(), MOVIENAME, MOVIEPOSTER, MOVIERATE)
                        viewModel.addToFavourites(movie)
                        favouriteIcon.setImageResource(R.drawable.offwhite_unfav_icon)
                        Toast.makeText(this, "Added To Favourites", Toast.LENGTH_SHORT).show()

                        checkREsult=true
                    }

                }
                movieRating.apply {
                    progressMax = 10f
                    roundBorder = true
                    setProgressWithAnimation(it.vote_average.toFloat(), 10)
                }
                castrecyclerview.apply {
                    if(it.credits.cast.isEmpty())
                    {
                        castrecyclerview.visibility=View.GONE
                        noCasttx.visibility=View.VISIBLE
                    }
                    else{
                        adapter = CastRecyclerAdapter(it.credits.cast)

                    }

                }

            })
        }else{
            setContentView(R.layout.no_internet_connection)

        }



    }
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}
