package com.example.kotlinstudy.repositiories

import androidx.lifecycle.MutableLiveData
import com.example.kotlinstudy.model.Movie
import javax.sql.DataSource

class MoviesDataSourceFactory: androidx.paging.DataSource.Factory<Int, Movie>() {


    val moviesLiveDataSource=MutableLiveData<MoviesDataSource>()
    lateinit var listId:String

    override fun create(): androidx.paging.DataSource<Int, Movie> {

        val moviesDataSource=MoviesDataSource(listId)
        moviesLiveDataSource.postValue(moviesDataSource)

        return moviesDataSource
    }
}