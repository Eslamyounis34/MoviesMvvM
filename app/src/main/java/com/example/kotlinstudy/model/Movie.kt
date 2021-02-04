package com.example.kotlinstudy.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*


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
