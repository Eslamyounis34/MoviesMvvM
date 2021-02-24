package com.moviesguide.kotlinstudy.model


data  class Movie (
    val id:String,
    val title:String,
    val overview:String,
    val poster_path: String,
    val vote_average:String,
    val backdrop_path :String,
    val credits:CastList,
    val runtime:String,
    val release_date:String,
    val genres:List<genere>


)
