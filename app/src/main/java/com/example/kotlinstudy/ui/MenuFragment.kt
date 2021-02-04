package com.example.kotlinstudy.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinstudy.R
import com.example.kotlinstudy.room.FavouritesMovies
import com.example.kotlinstudy.viewmodels.FavouritesViewModel
import com.example.kotlinstudy.viewmodels.HomeViewModel

/**
 * A simple [Fragment] subclass.
 */
class MenuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_menu, container, false)


        lateinit var viewModel: FavouritesViewModel
        lateinit var favouritesrecycler: RecyclerView
        lateinit var fav_adapter: FavouritesRecyclerAdapter

        favouritesrecycler = v.findViewById(R.id.favoritesrecyclerview)
        viewModel = ViewModelProvider(this).get(FavouritesViewModel::class.java)


        viewModel.getAllFavMovies().observe(viewLifecycleOwner, Observer {

            favouritesrecycler.apply {
                adapter = FavouritesRecyclerAdapter(it)
                fav_adapter = FavouritesRecyclerAdapter(it)

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
                    }
                }

                val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
                itemTouchHelper.attachToRecyclerView(favouritesrecycler)
            }
        })


        return v
    }

}
