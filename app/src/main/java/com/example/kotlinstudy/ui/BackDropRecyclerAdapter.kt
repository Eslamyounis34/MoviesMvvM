package com.example.kotlinstudy.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.model.BackDrop
import com.squareup.picasso.Picasso

class BackDropRecyclerAdapter(private val backDrops:List<BackDrop>):RecyclerView.Adapter<BackDropRecyclerAdapter.Viewholder>() {




    class Viewholder(itemView:View) : RecyclerView.ViewHolder(itemView)
    {
//        var backDropPath=itemView.backdropimage
//        var bdlayout=itemView.backdroprelative
        val backDropPath: ImageView
        val bdlayout: RelativeLayout

        init {

            backDropPath=itemView.findViewById(R.id.backdropimage)
            bdlayout=itemView.findViewById(R.id.backdroprelative)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var v=LayoutInflater.from(parent.context).inflate(R.layout.backdropitem,parent,false)
        return Viewholder(v)
    }

    override fun getItemCount()=backDrops.size

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        Picasso.get().load("https://image.tmdb.org/t/p/w342/"+backDrops[position].file_path).into(holder.backDropPath)

        holder.bdlayout.setOnClickListener {
            var intent=Intent(it.context,MovieImagePreview::class.java)
            intent.putExtra("ImagePath",backDrops[position].file_path)
            it.context.startActivity(intent)
        }


    }
}