package com.example.kotlinstudy.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinstudy.model.Movie
import com.example.kotlinstudy.repositiories.Homerepository

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val homerepository = Homerepository(application)

    val nowPlayingmoviesList: LiveData<List<Movie>>
    val topRatedmoviesList: LiveData<List<Movie>>
    val popularMoviesList: LiveData<List<Movie>>


    init {
        this.nowPlayingmoviesList = homerepository.getNowPlayingMovies()
        this.topRatedmoviesList = homerepository.getTopRatedMovies()
        this.popularMoviesList = homerepository.getPopularMovies()


    }


    fun getNowPlayingMovies(): LiveData<List<Movie>> {

        return nowPlayingmoviesList


    }

    fun getTopRatedMovies(): LiveData<List<Movie>> {
        return topRatedmoviesList

    }

    fun getPopularMovies(): LiveData<List<Movie>> {
        return popularMoviesList

    }

}
