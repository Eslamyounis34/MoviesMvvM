package com.example.kotlinstudy.ui

import android.icu.text.StringPrepParseException
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.data.Api
import com.example.kotlinstudy.model.MoviesList
import com.example.kotlinstudy.viewmodels.HomeViewModel
import com.example.kotlinstudy.viewmodels.SearchViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IndexOutOfBoundsException

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_search, container, false)

        lateinit var viewModel: SearchViewModel
        lateinit var searchRecyclerView:RecyclerView

       viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchRecyclerView=v.findViewById(R.id.searchrecyclerview)


        var search:EditText=v.findViewById(R.id.searchedittext)
        var searchtx:ImageView=v.findViewById(R.id.searchtextview)

        var disposable: Disposable? = null


        searchtx.setOnClickListener(View.OnClickListener {
            var query=search.text.toString()
            viewModel.getResults(query).observe(viewLifecycleOwner, Observer {
                searchRecyclerView.apply {
                     adapter=CategoryRecyclerAdapter(it)
                }
            })

//            val retrofit =  Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/movie/").addConverterFactory(
//                GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build()
//        val service=retrofit.create(Api::class.java)
//            disposable= service.getSearchResults("e76112b72c6c245384a5ecfd814a3ec2",query)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//
//                    searchrecyclerview.apply {
//                     adapter=CategoryRecyclerAdapter(it.results)
//                }
//                })


        })

        return v
    }

}
