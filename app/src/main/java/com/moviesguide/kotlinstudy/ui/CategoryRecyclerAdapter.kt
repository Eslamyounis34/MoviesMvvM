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
import com.moviesguide.kotlinstudy.model.Movie
import com.squareup.picasso.Picasso

class CategoryRecyclerAdapter (private val movies:List<Movie>) : RecyclerView.Adapter<CategoryRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name :TextView
        var username :ImageView
        var layout :RelativeLayout

        init {
            name=itemView.findViewById(R.id.title)
            username=itemView.findViewById(R.id.poster)
            layout=itemView.findViewById(R.id.relativelayout)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categoryrecycleritemrow, parent, false)
        return CategoryRecyclerAdapter.ViewHolder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(movies[position].title)
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movies[position].poster_path)
            .into(holder.username)

        holder.layout.setOnClickListener(View.OnClickListener {
            var intent = Intent(it.context, Details::class.java)
            intent.putExtra("MovieID", movies[position].id)
            it.context.startActivity(intent)
        })
    }
}