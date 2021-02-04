package com.example.kotlinstudy.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.kotlinstudy.R

class ViewPagerAdapter (private val context:Context):PagerAdapter() {


    private var layoutInflater:LayoutInflater?=null
    private val images= arrayOf(R.drawable.vpone,R.drawable.vptwo,R.drawable.vpthree)
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view=== `object`
    }



    override fun getCount(): Int {
        return images.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var image_pager:ImageView
        layoutInflater=context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v=layoutInflater!!.inflate(R.layout.viewpageritem,null)
        image_pager=v.findViewById(R.id.image_view_pager)
        image_pager.setImageResource(images[position])
        val vp=container as ViewPager
        vp.addView(v,0)

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp=container as ViewPager
        val v=`object` as View
        vp.removeView(v)
    }


}