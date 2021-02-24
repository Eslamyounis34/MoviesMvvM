package com.moviesguide.kotlinstudy.repositiories

import androidx.lifecycle.LiveData
import com.moviesguide.kotlinstudy.room.FavouritesMovies
import com.moviesguide.kotlinstudy.room.MyDAO

class FavaouritesRepository (private val favoraitesDOA: MyDAO) {


    val getAllMovies:LiveData<List<FavouritesMovies>>?=favoraitesDOA.getAllMovies()


    fun addToFavourites(movie:FavouritesMovies){
        favoraitesDOA.addMovie(movie)
    }

    fun deleteFromFavourites(movie:FavouritesMovies){
        favoraitesDOA.deleteMovie(movie)
    }
}