package com.example.kotlinstudy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.viewmodels.FavouritesViewModel

/**
 * A simple [Fragment] subclass.
 */
class FavouritesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        lateinit var viewModel: FavouritesViewModel
        lateinit var favouritesrecycler: RecyclerView
        lateinit var fav_adapter: FavouritesRecyclerAdapter
        var v = inflater.inflate(R.layout.fragment_favourites, container, false)

        val text: TextView = v.findViewById(R.id.nofavouritestextid)
        favouritesrecycler = v.findViewById(R.id.favoritesrecyclerview)
        var progressBar=v.findViewById<View>(R.id.fav_progress_bar) as ProgressBar
        viewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)

        progressBar.visibility=View.VISIBLE

        viewModel.getAllFavMovies().observe(viewLifecycleOwner, Observer {

            progressBar.visibility=View.GONE

            if (it.isEmpty())
            {
                favouritesrecycler.visibility=View.GONE
                text.visibility=View.VISIBLE
                text.setText("No Movies In Your List")
            }
            else {

                text.visibility=View.GONE
                favouritesrecycler.apply {
                    adapter = FavouritesRecyclerAdapter(it)
                    fav_adapter = FavouritesRecyclerAdapter(it)

                }


                //swipe to delete from room in REcyclerView

                val itemTouchHelperCallBack = object :
                    ItemTouchHelper.SimpleCallback(
                        0,
                        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                    ) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        (

                                viewModel.deleteFromFavourites(fav_adapter.getNoteAt(viewHolder.adapterPosition)))
                        if (viewHolder.adapterPosition == 0) {
                            favouritesrecycler.visibility = View.GONE
                            text.visibility = View.VISIBLE
                            text.setText("No Movies In Your List")
                        }


                    }
                }

                val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
                itemTouchHelper.attachToRecyclerView(favouritesrecycler)
            }
        })


        return v
    }

}
