package com.magednan.movieapp.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magednan.movieapp.R
import com.magednan.movieapp.databinding.MovieListItemBinding
import com.magednan.movieapp.model.Item

class MoviesAdapter :RecyclerView.Adapter<MoviesAdapter.MovieHolder>(){
    private var onItemClickListener: ((Item) -> Unit)? = null


    //differCallback to check for update
    private val differCallback = object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
return oldItem.id==newItem.id       }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
return oldItem==newItem        }
    }
    //AsyncListDiffer to do check on worker thread
    val differ = AsyncListDiffer(this, differCallback)

    inner class MovieHolder(val binding: MovieListItemBinding):RecyclerView.ViewHolder(binding.root){}

    //inflating the holder using dataBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val binding =
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)    }

    //Binding data in the holder
    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val item1 = differ.currentList[position]
        holder.binding.apply {
            movieListItemTvDetails.text ="the details:" +item1.fullTitle
            movieListItemTvRating.text = "the rate is :" +item1.imDbRating
            movieListItemTvTitle.text = "the title is :"+item1.title
            Glide.with(root).load(item1.image!!).centerCrop()
                .placeholder(R.drawable.place_holder).into(movieListItemIvPoster)
            movieListItemCvParent.setOnClickListener(View.OnClickListener {
                onItemClickListener?.let { it(item1) }
            })


        }



    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }



}