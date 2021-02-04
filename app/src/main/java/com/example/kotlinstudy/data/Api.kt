package com.example.kotlinstudy.data

import com.example.kotlinstudy.model.*
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.*

interface Api {


    @GET("now_playing")
    fun fetchNowPlayingMovies(@Query("api_key") key: String): Observable<MoviesList>

    @GET("popular")
    fun fetchPopularMovies(@Query("api_key") key: String): Observable<MoviesList>

    @GET("top_rated")
    fun fetchTopRatedMovies(@Query("api_key") key: String): Observable<MoviesList>

    @GET("{movie_id}/images")
    fun getBackDrops(
        @Path("movie_id") id: String,
        @Query("api_key") key: String
    ): Observable<BackDropList>

    @GET("{movie_id}")
    fun getMovieVideos(
        @Path("movie_id") id: String,
        @Query("api_key") key: String,
        @Query("append_to_response") credits: String
    ): Observable<VideosListObject>

    @GET("{id}")
    fun getMovieData(
        @Path("id") id: String,
        @Query("api_key") key: String,
        @Query("append_to_response") credits: String
    ): Observable<Movie>

    @GET("/3/search/movie")
    fun getSearchResults(
        @Query("api_key") api_key: String,
        @Query("query") query: String
    ): Observable<MoviesList>

    @GET("{categoryname}")
    fun getMoviesByCategoryName(@Path ("categoryname")category:String):
            Observable<MoviesList>

}