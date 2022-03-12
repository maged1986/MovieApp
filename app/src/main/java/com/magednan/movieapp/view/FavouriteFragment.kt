package com.magednan.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.magednan.movieapp.R
import com.magednan.movieapp.adapter.MoviesAdapter
import com.magednan.movieapp.databinding.FragmentFavouriteBinding
import com.magednan.movieapp.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment : Fragment(R.layout.fragment_favourite) {

    //vars
    private lateinit var binding: FragmentFavouriteBinding
    private val viewModel by viewModels<ViewModel>()
    private lateinit var moviesAdapter: MoviesAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // inflating the navigation
        binding = FragmentFavouriteBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        subscribeToObservers()
        //sending data to movieDetailsFragment
        moviesAdapter.setOnItemClickListener {
            if (it != null) {
                val bundle = Bundle().apply {
                    putSerializable("item", it)
                }
                NavHostFragment.findNavController(this).navigate(
                    R.id.action_favouriteFragment_to_movieDetailsFragment, bundle
                )
            }
        }

    }

   // setupRecyclerView
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        binding.favFragRv.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            moviesAdapter = MoviesAdapter()
            adapter = moviesAdapter
        }
    }

    private fun subscribeToObservers() {
        setupRecyclerView()
        //getting data from viewModel
        lifecycleScope.launch {
            viewModel.getItems().observe(viewLifecycleOwner, Observer {
               if(it.size >=1){
                moviesAdapter.differ.submitList(it)}
                else{
                   noData()
                }
            })
        }
    }

    private fun noData(){
          binding.errorLayout.visibility=View.VISIBLE
          binding.favFragRv.visibility=View.GONE


    }
}