package com.webronin_26.online_mart.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webronin_26.online_mart.data.remote.Response
import com.webronin_26.online_mart.Event
import com.webronin_26.online_mart.databinding.SearchItemBinding
import java.util.*
import kotlin.collections.ArrayList

class SearchAdapter (private val viewModel: SearchViewModel):
    ListAdapter<Response.SearchProduct, SearchAdapter.SearchViewHolder>(SearchDiffCallback()) {

    override fun submitList(list: MutableList<Response.SearchProduct>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) { holder.bind(viewModel , item)}
    }

    class SearchViewHolder private constructor(private  val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val id : String = UUID.randomUUID().toString()

        fun bind(viewModel: SearchViewModel , item: Response.SearchProduct) {
            viewModel.imageHashMap[id] = item.imageUrl
            viewModel.loadImage(item.imageUrl, binding.searchItemImageView, id)

            binding.viewmodel = viewModel
            binding.product = item
            binding.searchItemLinearLayout.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id" , item.id)
                bundle.putFloat("price", item.price)
                bundle.putString("name", item.name)
                viewModel.queryActivityBundle.value = Event(bundle)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from (parent: ViewGroup) : SearchViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = SearchItemBinding.inflate(layoutInflater , parent , false)
                return SearchViewHolder(binding)
            }
        }
    }
}

class SearchDiffCallback : DiffUtil.ItemCallback<Response.SearchProduct>() {

    override fun areContentsTheSame(oldItem: Response.SearchProduct, newItem: Response.SearchProduct): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Response.SearchProduct, newItem: Response.SearchProduct): Boolean {
        return oldItem == newItem
    }
}