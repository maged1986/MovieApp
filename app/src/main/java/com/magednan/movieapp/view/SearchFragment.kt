package com.magednan.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.magednan.movieapp.R
import com.magednan.movieapp.adapter.SearchAdapter
import com.magednan.movieapp.databinding.FragmentSearchBinding
import com.magednan.movieapp.model.HiddenItems
import com.magednan.movieapp.utils.Constants
import com.magednan.movieapp.utils.Status
import com.magednan.movieapp.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
   //vars
    lateinit var binding: FragmentSearchBinding
    val args: SearchFragmentArgs by navArgs()
    private val viewModel by viewModels<ViewModel>()
    private lateinit var searchAdapter: SearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // inflating the navigation
        binding = DataBindingUtil.bind<FragmentSearchBinding>(view)!!

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (args.search != null) {
            lifecycleScope.launch {
                viewModel.searchMovie(Constants.API_KEY, args.search)
            }
        }
        searchAdapter = SearchAdapter()
        searchAdapter.show = true
        subscribeToObservers()
        setupRecyclerView()
        searchAdapter.setOnHideButtonClickListener {
            lifecycleScope.launch {
                viewModel.upsertHiddenItems(HiddenItems(movie_id = it))
            }
        }
    }
//getting hiding movies ids from DB to hid them
    private fun hideMovies(list: ArrayList<com.magednan.movieapp.model.Result>)
            : List<com.magednan.movieapp.model.Result> {
        var listSize = list!!.size - 1
        var hiddenItemslist = viewModel.getHiddenItems()
        if(hiddenItemslist.size >=1 && listSize >=0){
            for (j in hiddenItemslist.indices -1) {
                for (i in 0 until listSize) {
                if (hiddenItemslist.get(j).movie_id.equals(list.get(i).id)) {
                    list.removeAt(i)
                    break
                }
            }
        }}
        return list ?: emptyList()
    }

    //setupRecyclerView
    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(context)
        binding.searcFragRv.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = searchAdapter
        }
    }

    private fun subscribeToObservers() {

        //getting data from viewModel and knowing the status
        viewModel.search.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    searchAdapter.differ.submitList(hideMovies(it.data!!.results))
                    searchAdapter.setOnItemClickListener {
                        if (it != null) {
                            val bundle = Bundle().apply {
                                putSerializable("item", it)
                            }
                            NavHostFragment.findNavController(this).navigate(
                                R.id.action_searchFragment_to_movieDetailsFragment, bundle
                            )
                        }
                    }

                }

                Status.ERROR -> {
                    Toast.makeText(
                        requireContext(),
                        it.message ?: "There is a network conncetion",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }

                Status.LOADING -> {
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()

                }
            }
        })
    }


}