package com.moviesguide.kotlinstudy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.moviesguide.kotlinstudy.model.Movie
import com.moviesguide.kotlinstudy.repositiories.SearchRepository

class SearchViewModel(application: Application):AndroidViewModel(application) {

    private  val searchRepo=SearchRepository(application)

    lateinit var   searchList: MutableLiveData<List<Movie>>

    fun getResults(query:String):MutableLiveData<List<Movie>>
    {
        searchList=searchRepo.getSearchResult(query)
        return searchList
    }


}