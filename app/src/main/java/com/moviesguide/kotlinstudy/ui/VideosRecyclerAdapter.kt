package com.moviesguide.kotlinstudy.ui

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moviesguide.kotlinstudy.R
import com.moviesguide.kotlinstudy.model.Video
import com.squareup.picasso.Picasso

class VideosRecyclerAdapter(private val videos:List<Video>):RecyclerView.Adapter<VideosRecyclerAdapter.ViewHolder>() {


    class ViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView)
    {
        var videoImage:ImageView
        var videoName:TextView
        init {
            videoImage=itemView.findViewById(R.id.videoimage)
            videoName=itemView.findViewById(R.id.videoname)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var v=LayoutInflater.from(parent.context).inflate(R.layout.recyclevideoitem,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount()=videos.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.videoName.setText(videos[position].name)
        holder.videoImage.setOnClickListener {
            //Toast.makeText(it.context,"i am video num # "+position,Toast.LENGTH_SHORT).show()
            val intent=Intent(Intent.ACTION_VIEW)
            intent.data= Uri.parse("https://www.youtube.com/watch?v="+videos[position].key)
                it.context.startActivity(intent)

        }
        Picasso.get().load("https://img.youtube.com/vi/"+videos[position].key+"/0.jpg").into(holder.videoImage)
    }
}