package com.moviesguide.kotlinstudy.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.moviesguide.kotlinstudy.model.BackDrop
import com.moviesguide.kotlinstudy.model.Movie
import com.moviesguide.kotlinstudy.model.Video
import com.moviesguide.kotlinstudy.repositiories.MovieDetailsRepository
import com.moviesguide.kotlinstudy.room.FavouritesMovies

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