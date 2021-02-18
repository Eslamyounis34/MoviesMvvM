package com.example.kotlinstudy.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.kotlinstudy.model.Movie
import com.example.kotlinstudy.repositiories.CategoryMoviesRepository
import com.example.kotlinstudy.repositiories.Homerepository
import com.example.kotlinstudy.repositiories.MoviesDataSource
import com.example.kotlinstudy.repositiories.MoviesDataSourceFactory

class CategoryMoviesViewModel(application: Application):AndroidViewModel(application) {

    private  val categoryrepository= CategoryMoviesRepository(application)
    val userPagedList : LiveData<PagedList<Movie>>
    private val liveDataSource : LiveData<MoviesDataSource>

    val itemDataSourceFactory = MoviesDataSourceFactory()


//    val  nowPlayingmoviesList: LiveData<List<Movie>>
//    val  topRatedmoviesList: LiveData<List<Movie>>
//    val  popularMoviesList: LiveData<List<Movie>>



    init {
//        this.nowPlayingmoviesList = categoryrepository.getNowPlayingMovies()
//        this.topRatedmoviesList = categoryrepository.getTopRatedMovies()
//        this.popularMoviesList = categoryrepository.getPopularMovies()


        liveDataSource = itemDataSourceFactory.moviesLiveDataSource
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MoviesDataSource.PAGE_SIZE)
            .build()

        userPagedList = LivePagedListBuilder(itemDataSourceFactory,config)
            .build()
        Log.e("CheckData",userPagedList.toString())

    }

    fun setListId(listId: String){
        itemDataSourceFactory.listId = listId
    }


//    fun getNowPlayingMovies(): LiveData<PagedList<Movie>>
//    {
//
//        return userPagedList
//
//    }

//    fun getTopRatedMovies(): LiveData<List<Movie>>
//    {
//        return topRatedmoviesList
//
//    }
//
//    fun getPopularMovies(): LiveData<List<Movie>>
//    {
//        return popularMoviesList
//
//    }

}