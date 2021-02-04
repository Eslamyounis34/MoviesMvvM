package com.example.kotlinstudy.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.model.Actor
import com.example.kotlinstudy.model.Movie
import com.squareup.picasso.Picasso

class CastRecyclerAdapter(private val actors:List<Actor>) : RecyclerView.Adapter<CastRecyclerAdapter.ViewHolder> (){



    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
//        var image=itemView.castimage
//        var text=itemView.castname

        var image:ImageView
        var text:TextView

        init {
            image=itemView.findViewById(R.id.castimage)
            text=itemView.findViewById(R.id.castname)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  v=LayoutInflater.from(parent.context).inflate(R.layout.actorrecycleritem,parent,false)
        return ViewHolder(v)

    }

    override fun getItemCount()=actors.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.setText(actors[position].name)
        Picasso.get().load("https://image.tmdb.org/t/p/w342/"+actors[position].profile_path).into(holder.image)
    }
}