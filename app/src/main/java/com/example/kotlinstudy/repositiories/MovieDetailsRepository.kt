package com.example.kotlinstudy.repositiories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.kotlinstudy.data.Api
import com.example.kotlinstudy.model.*
import com.example.kotlinstudy.room.FavouritesMovies
import com.example.kotlinstudy.room.MoviesDB
import com.example.kotlinstudy.room.MyDAO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MovieDetailsRepository(val application: Application) {
    //return movies data
    //return videos
    //return backdrops

    val backdropList = MutableLiveData<List<BackDrop>>()
    val videosList = MutableLiveData<List<Video>>()
    val movieData= MutableLiveData<Movie>()
    var disposable: Disposable? = null
    var exists:Boolean = false
    var dao: MyDAO?=null


    fun addMovieToFavourites(movie:FavouritesMovies){
        //dao=addMovieToFavourites(movie)
        dao= MoviesDB.getAppDataBase(application)!!.movieDao()
        dao?.addMovie(movie)
    }




    val retrofit =
        Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/").addConverterFactory(
            GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    val service=retrofit.create(Api::class.java)


    fun getMovieBackDrops(id:String):MutableLiveData<List<BackDrop>>
    {


       disposable= service.getBackDrops(id,"e76112b72c6c245384a5ecfd814a3ec2")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                backdropList.value=it.backdrops }

            )
        return backdropList
    }

    fun checkExist(id :Int): Boolean {
        dao= MoviesDB.getAppDataBase(application)!!.movieDao()
        exists=dao!!.exists(id)
        return exists
    }

    fun getMovieVideosList(id : String):MutableLiveData<List<Video>>
    {

     disposable=   service.getMovieVideos(id,"e76112b72c6c245384a5ecfd814a3ec2","videos")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                videosList.value=it.videos.results }

            )

        return videosList
    }

    fun getMovieData(id:String):MutableLiveData<Movie>
    {

      disposable=  service.getMovieData(id,"e76112b72c6c245384a5ecfd814a3ec2","credits")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                movieData.value=it
            }

            )
        return movieData
    }

}