package com.example.kotlinstudy.repositiories

import androidx.lifecycle.LiveData
import com.example.kotlinstudy.model.Movie
import com.example.kotlinstudy.room.FavouritesMovies
import com.example.kotlinstudy.room.MyDAO

class FavaouritesRepository (private val favoraitesDOA: MyDAO) {


    val getAllMovies:LiveData<List<FavouritesMovies>>?=favoraitesDOA.getAllMovies()


    fun addToFavourites(movie:FavouritesMovies){
        favoraitesDOA.addMovie(movie)
    }

    fun deleteFromFavourites(movie:FavouritesMovies){
        favoraitesDOA.deleteMovie(movie)
    }
}