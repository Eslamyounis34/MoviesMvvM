package com.example.kotlinstudy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.kotlinstudy.R
import com.example.kotlinstudy.data.Api
import com.example.kotlinstudy.model.MoviesList
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var bottomBar:BottomNavigationView


    val manager=supportFragmentManager
    private val mOnNavigationItemSelectedListener=BottomNavigationView.OnNavigationItemSelectedListener {
        when(it.itemId)
        {
            R.id.item0 ->{
                val fragment=SearchFragment()
                manager.beginTransaction()
                    .replace(R.id.container_layout,fragment)
                    .addToBackStack(fragment.toString())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.item1 ->{
                val fragment=HomeFragment()
                manager.beginTransaction()
                    .replace(R.id.container_layout,fragment)
                    .addToBackStack(fragment.toString())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.item2 ->{
                val fragment=MenuFragment()
                manager.beginTransaction()
                    .replace(R.id.container_layout,fragment)
                    .addToBackStack(fragment.toString())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomBar=findViewById(R.id.bottomBar)

        bottomBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_items,menu)
        return true
    }


}



