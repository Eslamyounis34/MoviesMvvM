package com.moviesguide.kotlinstudy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moviesguide.kotlinstudy.R
import com.moviesguide.kotlinstudy.viewmodels.CategoryMoviesViewModel

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        var date = intent.getSerializableExtra("CategoryData")
        lateinit var categoryrecycler: RecyclerView
        categoryrecycler = findViewById(R.id.categoryrecycler)

        lateinit var viewModel: CategoryMoviesViewModel

        viewModel = ViewModelProvider(this).get(CategoryMoviesViewModel::class.java)
        viewModel.setListId(date as String)


        viewModel.userPagedList.observe(this, Observer {
            val pgadapter = MoviesCategoryPagingAdapter()
            pgadapter.submitList(it)

            categoryrecycler.layoutManager = GridLayoutManager(this, 2)
            categoryrecycler.adapter = pgadapter
        })


    }
}
