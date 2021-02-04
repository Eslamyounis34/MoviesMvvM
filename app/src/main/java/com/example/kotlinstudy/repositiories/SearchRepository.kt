package com.example.kotlinstudy.repositiories

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.kotlinstudy.data.Api
import com.example.kotlinstudy.model.Movie
import com.example.kotlinstudy.model.MoviesList
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SearchRepository (val application: Application){


   var  searchResultList:MutableLiveData<List<Movie>> = MutableLiveData()

    var disposable: Disposable? = null


    fun getSearchResult(query:String):MutableLiveData<List<Movie>>
    {
        val retrofit =  Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/").addConverterFactory(
            GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        val service=retrofit.create(Api::class.java)

        disposable=service.getSearchResults("e76112b72c6c245384a5ecfd814a3ec2",query)
            .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
            searchResultList.value=it.results }

        )
        return searchResultList
    }

}