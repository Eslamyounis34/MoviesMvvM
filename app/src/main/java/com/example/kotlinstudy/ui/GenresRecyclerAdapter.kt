package com.example.kotlinstudy.ui

import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.model.genere

class GenresRecyclerAdapter(private val genresList:List<genere>):RecyclerView.Adapter<GenresRecyclerAdapter.ViewHolder>(){


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var name:TextView
        init {
            name=itemView.findViewById(R.id.genresname)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v=LayoutInflater.from(parent.context).inflate(R.layout.genresrowitem,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount() =genresList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.setText(genresList[position].name)
    }
}