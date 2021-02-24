package com.moviesguide.kotlinstudy.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.moviesguide.kotlinstudy.R
import com.moviesguide.kotlinstudy.viewmodels.SearchViewModel

/**
 * A simple [Fragment] subclass.
 */
class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        lateinit var v: View
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_search, container, false)

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
            if (isNetworkAvailable()) {
                var query = search.text.toString()
                progressBar.visibility = View.VISIBLE
                if (query.isEmpty()) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, "Please Enter a valied Query", Toast.LENGTH_SHORT)
                        .show()
                } else {
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

            } else {
                Toast.makeText(context, "No Network ", Toast.LENGTH_SHORT).show()

            }
        })

        return v
    }

    fun View.hideKeyboard() {
        val inputMethodManager =
            context.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }
}
