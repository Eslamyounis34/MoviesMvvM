package com.moviesguide.kotlinstudy.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moviesguide.kotlinstudy.R
import com.moviesguide.kotlinstudy.room.FavouritesMovies
import com.squareup.picasso.Picasso

class FavouritesRecyclerAdapter(private val favoriteMovies:List<FavouritesMovies>):RecyclerView.Adapter<FavouritesRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        var movieImage:ImageView
        var movieName:TextView
        var relativeLayout:RelativeLayout
        var movieRate:TextView

        init {
            movieImage=itemView.findViewById(R.id.favouriteimageView)
            movieName=itemView.findViewById(R.id.favouritetextView)
            relativeLayout=itemView.findViewById(R.id.favouritelayout)
            movieRate=itemView.findViewById(R.id.mfavouritesovierate)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v= LayoutInflater.from(parent.context).inflate(R.layout.favoritesrecycleritem,parent,false)
        return FavouritesRecyclerAdapter.ViewHolder(v)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieName.setText(favoriteMovies[position].movie_Name)
        holder.movieRate.setText(favoriteMovies[position].movie_Rate)
        Picasso.get().load("https://image.tmdb.org/t/p/w342/"+favoriteMovies[position].movie_Poster).into(holder.movieImage)
        holder.relativeLayout.setOnClickListener {
            var intent = Intent(it.context, Details::class.java)

            intent.putExtra("MovieID", favoriteMovies[position].movie_Id.toString())

            it.context.startActivity(intent)
        }
    }

   fun getNoteAt(position: Int): FavouritesMovies {
       return favoriteMovies.get(position)
   }

    override fun getItemCount():Int {
        return favoriteMovies.size
    }

    }


