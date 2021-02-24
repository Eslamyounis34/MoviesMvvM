package com.moviesguide.kotlinstudy.repositiories

import android.app.Application
import androidx.paging.PageKeyedDataSource
import com.moviesguide.kotlinstudy.data.Api
import com.moviesguide.kotlinstudy.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MoviesDataSource (private val listId: String
): PageKeyedDataSource<Int, Movie>() {

    lateinit var application: Application
    var disposable: Disposable? = null
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {

        val retrofit =
            Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val service = retrofit.create(Api::class.java)

        disposable =
            service.fetchMoviesByPage(listId,"e76112b72c6c245384a5ecfd814a3ec2", FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val responseItems = it.results
                    responseItems.let {
                        callback.onResult(responseItems, null, FIRST_PAGE + 1)
                    }
                })


    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val retrofit =
            Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val service = retrofit.create(Api::class.java)

        disposable =
            service.fetchMoviesByPage(listId,"e76112b72c6c245384a5ecfd814a3ec2", params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val responseItems = it.results
                    val key = if (params.key >1 )params.key -1 else 0
                    responseItems.let {
                        callback.onResult(responseItems, key)
                    }
                })    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        val retrofit =
            Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val service = retrofit.create(Api::class.java)

        disposable =
            service.fetchMoviesByPage(listId,"e76112b72c6c245384a5ecfd814a3ec2", params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val responseItems = it.results
                    val key = params.key + 1
                    responseItems.let {
                        callback.onResult(responseItems, key)
                    }
                })
    }


    companion object {
        const val PAGE_SIZE = 10
        const val FIRST_PAGE = 1

    }


}