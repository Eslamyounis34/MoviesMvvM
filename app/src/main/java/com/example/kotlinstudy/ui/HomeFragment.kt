package com.example.kotlinstudy.ui

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.kotlinstudy.R
import com.example.kotlinstudy.data.Api
import com.example.kotlinstudy.model.Movie
import com.example.kotlinstudy.model.MoviesList
import com.example.kotlinstudy.viewmodels.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        lateinit var viewModel: HomeViewModel
        lateinit var nowPlayingrecycler: RecyclerView
        lateinit var topratedrecycler: RecyclerView
        lateinit var popularrecycler: RecyclerView


        var toprate = root.findViewById<View>(R.id.topratedicon) as ImageView
        var popular = root.findViewById<View>(R.id.popularicon) as ImageView
        var nowplaying = root.findViewById<View>(R.id.nowplayingicon) as ImageView
        var topratedtx=root.findViewById<View>(R.id.topratedtx) as TextView
        var progressBar=root.findViewById<View>(R.id.progress_bar) as ProgressBar

        // Inflate the layout for this fragment

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        nowPlayingrecycler = root.findViewById(R.id.nowplayingrecycler)
        topratedrecycler = root.findViewById(R.id.topratedrecycler)
        popularrecycler = root.findViewById(R.id.popularrecycler)

        progressBar.visibility=View.VISIBLE



        viewModel.getNowPlayingMovies().observe(viewLifecycleOwner, Observer {
            nowPlayingrecycler.apply {
                progressBar.visibility=View.GONE
                adapter = MovieAdapter(it)
                nowplaying.setOnClickListener(View.OnClickListener {
                    var intent = Intent(requireContext()!!, CategoryActivity::class.java)
                    intent.putExtra("CategoryData", "now_playing")
                    startActivity(intent)
                })
            }
        })

        viewModel.getTopRatedMovies().observe(viewLifecycleOwner, Observer {
            topratedrecycler.apply {
                adapter = MovieAdapter(it)

                toprate.setOnClickListener(View.OnClickListener {
                    var intent = Intent(requireContext()!!, CategoryActivity::class.java)
                    intent.putExtra("CategoryData", "top_rated")
                    startActivity(intent)
                })
            }
        })

        viewModel.getPopularMovies().observe(viewLifecycleOwner, Observer {
            popularrecycler.apply {
                adapter = MovieAdapter(it)

                popular.setOnClickListener(View.OnClickListener {
                    var intent = Intent(requireContext()!!, CategoryActivity::class.java)
                    intent.putExtra("CategoryData", "popular")
                    startActivity(intent)
                })

            }
        })


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        lateinit var imag_Slider: ImageSlider
        imag_Slider = view.findViewById(R.id.image_slider)

        var retofit = Retrofit.Builder()

            .baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        var api = retofit.create(Api::class.java)
        api.fetchUpComingMovies("e76112b72c6c245384a5ecfd814a3ec2")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val imageList = ArrayList<SlideModel>() // Create image list

                imageList.add(
                    SlideModel(
                        "https://image.tmdb.org/t/p/w1280/" + it.results[1].poster_path,
                        it.results[1].title
                    )
                )
                imageList.add(
                    SlideModel(
                        "https://image.tmdb.org/t/p/w1280/" + it.results[2].poster_path,
                        it.results[2].title
                    )
                )
                imageList.add(
                    SlideModel(
                        "https://image.tmdb.org/t/p/w1280/" + it.results[3].poster_path,
                        it.results[3].title
                    )
                )

                imageList.add(
                    SlideModel(
                        "https://image.tmdb.org/t/p/w1280/" + it.results[4].poster_path,
                        it.results[4].title
                    )
                )

                imageList.add(
                    SlideModel(
                        "https://image.tmdb.org/t/p/w1280/" + it.results[5].poster_path,
                        it.results[5].title
                    )
                )
                imageList.add(
                    SlideModel(
                        "https://image.tmdb.org/t/p/w1280/" + it.results[6].poster_path,
                        it.results[6].title
                    )
                )


                imag_Slider.setImageList(imageList)

                imag_Slider.setItemClickListener(object : ItemClickListener {
                    override fun onItemSelected(position: Int) {
                        var intent = Intent(context, Details::class.java)
                        intent.putExtra("MovieID", it.results[position+1].id)
                        startActivity(intent)
                    }

                })

            })

    }

    private fun downloadFile(fileName: String, desc: String, url: String) {

        val request = DownloadManager.Request(Uri.parse(url))
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            .setTitle(fileName)
            .setDescription(desc)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(false)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
        val downloadManager =
            requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadID = downloadManager.enqueue(request)
    }
}
