package com.moviesguide.kotlinstudy.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [FavouritesMovies::class], version = 2)
abstract class MoviesDB : RoomDatabase() {

    abstract fun movieDao(): MyDAO


    companion object {
        @Volatile
        private var INSTANCE: MoviesDB? = null


        val migration_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }

        fun getAppDataBase(context: android.content.Context): MoviesDB? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder<MoviesDB>(
                    context.applicationContext, MoviesDB::class.java, "AppDBB"
                )
                    .addMigrations(migration_1_2)
                    .allowMainThreadQueries()
                    .build()

            }
            return INSTANCE
        }
    }

}