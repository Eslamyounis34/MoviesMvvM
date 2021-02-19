package com.example.kotlinstudy.ui

import android.app.Activity
import android.content.Context
import android.icu.text.StringPrepParseException
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat.getSystemService
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
        val v = inflater.inflate(R.layout.fragment_search, container, false)

        lateinit var viewModel: SearchViewModel
        lateinit var searchRecyclerView: RecyclerView
        var searchlayout: RelativeLayout

        searchlayout = v.findViewById(R.id.searchfraglayout)
        var progressBar = v.findViewById<View>(R.id.search_progress_bar) as ProgressBar


        searchlayout.setOnClickListener(View.OnClickListener {
            searchlayout.hideKeyboard()
        })



        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchRecyclerView = v.findViewById(R.id.searchrecyclerview)

        var search: EditText = v.findViewById(R.id.searchedittext)
        var searchtx: ImageView = v.findViewById(R.id.searchtextview)

        searchtx.setOnClickListener(View.OnClickListener {
            var query = search.text.toString()
            progressBar.visibility = View.VISIBLE
            if (query.isEmpty())
            {
                progressBar.visibility = View.GONE
                Toast.makeText(context,"Please Enter a valied Query",Toast.LENGTH_SHORT).show()
            }
            else{
                viewModel.getResults(query).observe(viewLifecycleOwner, Observer {
                    if (it.isEmpty()) {
                        progressBar.visibility = View.GONE
                        Toast.makeText(context, "No Results ", Toast.LENGTH_SHORT).show()

                    } else {


                    searchRecyclerView.apply {
                        progressBar.visibility = View.GONE
                        adapter = CategoryRecyclerAdapter(it)
                    }
                }
                })
            }


        })

        return v
    }

    fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }


}
