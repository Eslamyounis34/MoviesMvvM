package com.example.kotlinstudy.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.kotlinstudy.model.Movie

@Dao
interface MyDAO {


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMovie(movie: FavouritesMovies)

    @Query("SELECT * FROM favourites")
    fun getAllMovies():LiveData<List<FavouritesMovies>>

    @Delete
    fun deleteMovie(movie:FavouritesMovies)

    @Query("SELECT EXISTS (SELECT 1 FROM favourites WHERE movie_ID = :id)")
    fun exists(id: Int): Boolean

}