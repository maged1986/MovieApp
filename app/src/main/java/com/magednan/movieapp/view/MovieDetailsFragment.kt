package com.magednan.movieapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.magednan.movieapp.R
import com.magednan.movieapp.databinding.FragmentMovieDetailsBinding
import com.magednan.movieapp.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    //vars
    private lateinit var binding: FragmentMovieDetailsBinding
    val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel by viewModels<ViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=DataBindingUtil.bind<FragmentMovieDetailsBinding>(view)!!

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
            val item=args.item


        //binding data to views
            binding.apply {
                Glide.with(root).load(item!!.image).into(detailsFragIvImg)
                detailsFragTvDesc.text=item!!.fullTitle.toString()
                detailsFragTvRating.text=item!!.imDbRating.toString()
                detailsFragTvTitle.text=item!!.title.toString()
                detailsFragFabAddToFav.setOnClickListener {
                    lifecycleScope.launch {
                        viewModel.upsert(item)
                        Toast.makeText(requireContext(),"item saved",Toast.LENGTH_LONG).show()
                    }
                }
            }

    }

}