package com.magednan.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.magednan.movieapp.R
import com.magednan.movieapp.adapter.MoviesAdapter
import com.magednan.movieapp.databinding.FragmentApiBinding
import com.magednan.movieapp.utils.Constants.API_KEY
import com.magednan.movieapp.utils.Status
import com.magednan.movieapp.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ApiFragment : Fragment(R.layout.fragment_api) {

    //vars
    lateinit var binding:FragmentApiBinding
    private val viewModel by viewModels<ViewModel>()
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // inflating the navigation
        binding= DataBindingUtil.bind<FragmentApiBinding>(view)!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycleScope.launch {
           viewModel.getPopularMovie(API_KEY)
       }
        moviesAdapter= MoviesAdapter()
        subscribeToObservers()
        setupRecyclerView()


    }

   // setupRecyclerView
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        binding.apiFragRv.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = moviesAdapter
        }
    }

    private fun subscribeToObservers() {
        //getting data from viewModel and knowing the ststus
        viewModel.movie.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    moviesAdapter.differ.submitList(it.data?.items)

                    //sending data to movieDetailsFragment
                    moviesAdapter.setOnItemClickListener {
                        if(it !=null){
                            val bundle = Bundle().apply {
                                putSerializable("item", it)
                            }
                            findNavController(this).navigate(
                                R.id.action_apiFragment_to_movieDetailsFragment,bundle
                            )
                        }}
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "There is a network conncetion", Toast.LENGTH_LONG)
                        .show()
                }

                Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()

                }
            }
        })
    }


}