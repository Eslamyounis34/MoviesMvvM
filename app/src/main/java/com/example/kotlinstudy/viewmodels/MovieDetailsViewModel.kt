package com.example.kotlinstudy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kotlinstudy.model.BackDrop
import com.example.kotlinstudy.model.Movie
import com.example.kotlinstudy.model.Video
import com.example.kotlinstudy.repositiories.MovieDetailsRepository
import com.example.kotlinstudy.room.FavouritesMovies
import com.example.kotlinstudy.room.MyDAO

class MovieDetailsViewModel(application: Application): AndroidViewModel(application) {

    private  val detailsRepo=MovieDetailsRepository(application)

    lateinit var   backDropList: LiveData<List<BackDrop>>
    lateinit var   trailersList: LiveData<List<Video>>
    lateinit var   allMovieData: LiveData<Movie>
     var   checkExist=false



    fun getMovieImages(id:String ):LiveData<List<BackDrop>>
    {
        backDropList=detailsRepo.getMovieBackDrops(id)
        return backDropList

    }

    fun getMovieVideos(id:String ):LiveData<List<Video>>
    {
        trailersList=detailsRepo.getMovieVideosList(id)
        return trailersList

    }

    fun getMoviesData(id:String):LiveData<Movie>
    {
        allMovieData=detailsRepo.getMovieData(id)
        return allMovieData
    }

    fun addToFavourites(movie: FavouritesMovies){
        detailsRepo.addMovieToFavourites(movie)
    }

    fun checkExists(id: Int):Boolean{
       checkExist= detailsRepo.checkExist(id)
        return checkExist

    }
}