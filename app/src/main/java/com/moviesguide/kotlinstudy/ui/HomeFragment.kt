package com.moviesguide.kotlinstudy.ui

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.moviesguide.kotlinstudy.R
import com.moviesguide.kotlinstudy.data.Api
import com.moviesguide.kotlinstudy.viewmodels.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        lateinit var root:View

        if (isNetworkAvailable()) {
             root = inflater.inflate(R.layout.fragment_home, container, false)
            lateinit var viewModel: HomeViewModel
            lateinit var nowPlayingrecycler: RecyclerView
            lateinit var topratedrecycler: RecyclerView
            lateinit var popularrecycler: RecyclerView


            var toprate = root.findViewById<View>(R.id.topratedicon) as ImageView
            var popular = root.findViewById<View>(R.id.popularicon) as ImageView
            var nowplaying = root.findViewById<View>(R.id.nowplayingicon) as ImageView
            var topratedtx = root.findViewById<View>(R.id.topratedtx) as TextView
            var progressBar = root.findViewById<View>(R.id.progress_bar) as ProgressBar
            var darkSwitch=root.findViewById<View>(R.id.darkmodebutton) as SwitchCompat
            var darkModeIcon=root.findViewById<View>(R.id.darkmodeicon) as ImageView




            // Inflate the layout for this fragment

            viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
            nowPlayingrecycler = root.findViewById(R.id.nowplayingrecycler)
            topratedrecycler = root.findViewById(R.id.topratedrecycler)
            popularrecycler = root.findViewById(R.id.popularrecycler)

            progressBar.visibility = View.VISIBLE

            var sharedPreferences=activity?.getSharedPreferences("night",0)
            val value:Boolean=sharedPreferences!!.getBoolean("night_mode",true)

            if (value){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                darkSwitch.isChecked=true
                darkModeIcon.setImageResource(R.drawable.ic_light_color_mode)
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkSwitch.isChecked=false
                darkModeIcon.setImageResource(R.drawable.ic_light_on)

            }
            darkSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    darkSwitch.isChecked=true
                    val editor:SharedPreferences.Editor=sharedPreferences.edit()
                    editor.putBoolean("night_mode",true)
                    editor.commit()
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    darkSwitch.isChecked=false
                    val editor:SharedPreferences.Editor=sharedPreferences.edit()
                    editor.putBoolean("night_mode",false)
                    editor.commit()
                }
            })


            viewModel.getNowPlayingMovies().observe(viewLifecycleOwner, Observer {
                nowPlayingrecycler.apply {
                    progressBar.visibility = View.GONE
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
        } else {
            root = inflater.inflate(R.layout.no_internet_connection, container, false)
        }
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (isNetworkAvailable()) {
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
                            intent.putExtra("MovieID", it.results[position + 1].id)
                            startActivity(intent)
                        }

                    })

                })
        } else {
            Toast.makeText(context, "Not Available", Toast.LENGTH_SHORT).show()

        }


    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }

}
