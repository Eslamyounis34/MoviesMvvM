package com.example.kotlinstudy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.kotlinstudy.model.Movie
import com.example.kotlinstudy.repositiories.CategoryMoviesRepository
import com.example.kotlinstudy.repositiories.Homerepository

class CategoryMoviesViewModel(application: Application):AndroidViewModel(application) {

    private  val categoryrepository= CategoryMoviesRepository(application)

    val  nowPlayingmoviesList: LiveData<List<Movie>>
    val  topRatedmoviesList: LiveData<List<Movie>>
    val  popularMoviesList: LiveData<List<Movie>>



    init {
        this.nowPlayingmoviesList = categoryrepository.getNowPlayingMovies()
        this.topRatedmoviesList = categoryrepository.getTopRatedMovies()
        this.popularMoviesList = categoryrepository.getPopularMovies()


    }


    fun getNowPlayingMovies(): LiveData<List<Movie>>
    {
        return nowPlayingmoviesList

    }

    fun getTopRatedMovies(): LiveData<List<Movie>>
    {
        return topRatedmoviesList

    }

    fun getPopularMovies(): LiveData<List<Movie>>
    {
        return popularMoviesList

    }

}