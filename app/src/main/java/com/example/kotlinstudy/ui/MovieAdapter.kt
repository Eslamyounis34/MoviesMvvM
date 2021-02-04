package com.example.kotlinstudy.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.model.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(private val users:List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
    var name:TextView
    var poster:ImageView
    var layout:RelativeLayout
    init {
        name=itemView.findViewById(R.id.title)
        poster=itemView.findViewById(R.id.poster)
        layout=itemView.findViewById(R.id.relativelayout)
    }

}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycleritemrow,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount()=users.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(users[position].title)
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+users[position].poster_path).into(holder.poster)

        holder.layout.setOnClickListener(View.OnClickListener {
            var intent=Intent(it.context, Details::class.java)
            intent.putExtra("MovieID",users[position].id)
            it.context.startActivity(intent)

         Toast.makeText(it.context,"item number"+position.toString(),Toast.LENGTH_SHORT).show()

        })
    }

}