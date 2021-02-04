package com.example.kotlinstudy.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.data.Api
import com.example.kotlinstudy.model.MoviesList
import com.example.kotlinstudy.viewmodels.CategoryMoviesViewModel
import com.example.kotlinstudy.viewmodels.HomeViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        var date=intent.getSerializableExtra("CategoryData")
        lateinit var  categoryrecycler: RecyclerView
        categoryrecycler=findViewById(R.id.categoryrecycler)

        lateinit var viewModel: CategoryMoviesViewModel

        viewModel=ViewModelProvider(this).get(CategoryMoviesViewModel::class.java)

        if (date.toString()=="now_playing"){
            viewModel.getNowPlayingMovies().observe(this, Observer {
                categoryrecycler.apply {
                    layoutManager=GridLayoutManager(context,2)
                    adapter= CategoryRecyclerAdapter(it)

                }
            })
        }else if (date.toString()=="popular"){
            viewModel.getPopularMovies().observe(this, Observer {
                categoryrecycler.apply {
                    layoutManager=GridLayoutManager(context,2)
                    adapter= CategoryRecyclerAdapter(it)

                }
            })
        }else if(date.toString()=="top_rated"){
            viewModel.getTopRatedMovies().observe(this, Observer {
                categoryrecycler.apply {
                    layoutManager=GridLayoutManager(context,2)
                    adapter= CategoryRecyclerAdapter(it)

                }
            })
        }


    }
}
