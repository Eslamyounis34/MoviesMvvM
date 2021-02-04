package com.example.kotlinstudy.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import org.checkerframework.common.aliasing.qual.Unique

@Entity(tableName = "favourites",indices = [Index(value = ["movie_ID"], unique = true)])


data class FavouritesMovies (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Int,
    @ColumnInfo(name = "movie_ID")
    val movie_Id:Int,
    @ColumnInfo(name ="movie_Name" )
    var movie_Name:String,
    @ColumnInfo(name = "movie_Poster")
    var movie_Poster:String,
    @ColumnInfo(name="movie_Rate")
    var movie_Rate:String

    )