package com.moviesguide.kotlinstudy.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.moviesguide.kotlinstudy.R
import com.moviesguide.kotlinstudy.model.Movie
import com.squareup.picasso.Picasso

class MoviesCategoryPagingAdapter() : PagedListAdapter<Movie,MoviesCategoryPagingAdapter.ViewHolder>(
    USER_COMPARATOR){




    class ViewHolder(view:View):RecyclerView.ViewHolder(view){

        var name : TextView
        var username : ImageView
        var layout : RelativeLayout

        init {
            name=itemView.findViewById(R.id.title)
            username=itemView.findViewById(R.id.poster)
            layout=itemView.findViewById(R.id.relativelayout)

        }
    }
    companion object {
        private val USER_COMPARATOR = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                newItem == oldItem

        }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.categoryrecycleritemrow, parent, false)
        return MoviesCategoryPagingAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //get Item is very imp. for calling loadAfter()
        val movie=getItem(position)

        holder.name.setText(movie?.title)
        Picasso.get().load("https://image.tmdb.org/t/p/w500/" + movie?.poster_path)
            .into(holder.username)

        holder.layout.setOnClickListener(View.OnClickListener {
            var intent = Intent(it.context, Details::class.java)
            intent.putExtra("MovieID", movie?.id)
            it.context.startActivity(intent)
        })    }
}