package com.moviesguide.kotlinstudy.repositiories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.moviesguide.kotlinstudy.data.Api
import com.moviesguide.kotlinstudy.model.Movie
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CategoryMoviesRepository(val application: Application) {

    val nowPlayinMmoviesList = MutableLiveData<List<Movie>>()
    val topRatedMoviesList = MutableLiveData<List<Movie>>()
    val popularMoviesList = MutableLiveData<List<Movie>>()

    var disposable: Disposable? = null


    fun getNowPlayingMovies(): MutableLiveData<List<Movie>> {
        val retrofit =
            Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val service = retrofit.create(Api::class.java)

        disposable = service.fetchNowPlayingMovies("e76112b72c6c245384a5ecfd814a3ec2")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                nowPlayinMmoviesList.value = it.results
            })

        return nowPlayinMmoviesList

    }

    fun getTopRatedMovies(): MutableLiveData<List<Movie>> {
        val retrofit =
            Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val service = retrofit.create(Api::class.java)

        disposable = service.fetchTopRatedMovies("e76112b72c6c245384a5ecfd814a3ec2")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                topRatedMoviesList.value = it.results
            })
        return topRatedMoviesList

    }

    fun getPopularMovies(): MutableLiveData<List<Movie>> {
        val retrofit =
            Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        val service = retrofit.create(Api::class.java)

        disposable = service.fetchPopularMovies("e76112b72c6c245384a5ecfd814a3ec2")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                popularMoviesList.value = it.results
            })

        return popularMoviesList
    }
}