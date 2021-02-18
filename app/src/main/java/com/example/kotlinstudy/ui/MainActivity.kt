package com.example.kotlinstudy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import com.example.kotlinstudy.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomBar: BottomNavigationView


    val manager = supportFragmentManager
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item0 -> {
                    loadFragment(SearchFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.item1 -> {
                    loadFragment(HomeFragment())

                    return@OnNavigationItemSelectedListener true
                }
                R.id.item2 -> {
                   loadFragment(FavouritesFragment())
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomBar = findViewById(R.id.bottomBar)
        bottomBar.setSelectedItemId(R.id.item1);

        bottomBar.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        loadFragment(HomeFragment())

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_navigation_items, menu)
        return true
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container_layout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}



