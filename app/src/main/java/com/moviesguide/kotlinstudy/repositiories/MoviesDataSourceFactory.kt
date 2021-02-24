package com.moviesguide.kotlinstudy.repositiories

import androidx.lifecycle.MutableLiveData
import com.moviesguide.kotlinstudy.model.Movie

class MoviesDataSourceFactory: androidx.paging.DataSource.Factory<Int, Movie>() {


    val moviesLiveDataSource=MutableLiveData<MoviesDataSource>()
    lateinit var listId:String

    override fun create(): androidx.paging.DataSource<Int, Movie> {

        val moviesDataSource=MoviesDataSource(listId)
        moviesLiveDataSource.postValue(moviesDataSource)

        return moviesDataSource
    }
}