package com.moviesguide.kotlinstudy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.moviesguide.kotlinstudy.repositiories.FavaouritesRepository
import com.moviesguide.kotlinstudy.room.FavouritesMovies
import com.moviesguide.kotlinstudy.room.MoviesDB

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