package com.magednan.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.magednan.movieapp.R
import com.magednan.movieapp.databinding.MovieListItemBinding
import com.magednan.movieapp.model.Item
import com.magednan.movieapp.model.Result

class SearchAdapter :RecyclerView.Adapter<SearchAdapter.SearchHolder>(){
    private var onItemClickListener: ((Item) -> Unit)? = null
    private var onItemListener: ((String) -> Unit)? = null
    var show=false
    //differCallback to check for update
        private val differCallback = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id==newItem.id             }

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem==newItem                }
        }

        //AsyncListDiffer to do check on worker thread
        val differ = AsyncListDiffer(this, differCallback)
        inner class SearchHolder(val binding: MovieListItemBinding): RecyclerView.ViewHolder(binding.root){}
    //inflating the holder using dataBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val binding =
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHolder(binding)     }
    //Binding data in the holder
    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        val item = differ.currentList[position]
        holder.binding.apply {
            movieListItemTvDetails.text ="the details:" +item.description
            movieListItemTvRating.text = "the rate is :" +item.title
            movieListItemTvTitle.text = "the title is :"+item.title
            Glide.with(root).load(item.image!!).centerCrop()
                .placeholder(R.drawable.place_holder).into(movieListItemIvPoster)
        movieListItemCvParent.setOnClickListener{
            onItemClickListener?.let { it(Item(item.description,item.id,item.resultType,item.image,item.title
            )) }}
            movieListItemBtnHide.setOnClickListener {
                onItemListener?.let { it1 -> it1(item.id) }

        }
            if(show){holder.binding.movieListItemBtnHide.visibility= View.VISIBLE
            }else{
                holder.binding.movieListItemBtnHide.visibility= View.GONE
            }
        }       }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    fun setOnItemClickListener(listener: (Item) -> Unit) {
        onItemClickListener = listener
    }
    fun setOnHideButtonClickListener(listener: (String) -> Unit) {
        onItemListener = listener
    }

}
