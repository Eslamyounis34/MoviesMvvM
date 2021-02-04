package com.example.kotlinstudy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.kotlinstudy.model.Movie
import com.example.kotlinstudy.repositiories.FavaouritesRepository
import com.example.kotlinstudy.room.FavouritesMovies
import com.example.kotlinstudy.room.MoviesDB
import com.example.kotlinstudy.room.MyDAO

class FavouritesViewModel(application: Application) :AndroidViewModel(application) {

    private val favaouritesRepository:FavaouritesRepository
    val allFavourites: LiveData<List<FavouritesMovies>>

    init {
        val favDao = MoviesDB.getAppDataBase(application)!!.movieDao()!!
        favaouritesRepository= FavaouritesRepository(favDao)
        allFavourites= favaouritesRepository.getAllMovies!!
    }

    fun getAllFavMovies():LiveData<List<FavouritesMovies>> {
        return allFavourites
    }

    fun addMovieToFavourites(movie: FavouritesMovies){
    favaouritesRepository.addToFavourites(movie)
    }

    fun deleteFromFavourites(movie: FavouritesMovies){
        favaouritesRepository.deleteFromFavourites(movie)
    }
}