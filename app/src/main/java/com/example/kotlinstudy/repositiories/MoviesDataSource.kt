package com.example.kotlinstudy.repositiories

import android.app.Application
import androidx.paging.PageKeyedDataSource
import com.example.kotlinstudy.model.Movie

class MoviesDataSource : PageKeyedDataSource<Int, Movie>() {

    lateinit var application: Application
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        TODO("Not yet implemented")
        //MAke Initial Request

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("Not yet implemented")
        //try
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        TODO("Not yet implemented")
        //try
    }


}