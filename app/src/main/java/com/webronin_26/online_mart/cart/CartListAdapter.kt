package com.webronin_26.online_mart.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webronin_26.online_mart.data.remote.Response
import com.webronin_26.online_mart.databinding.CartItemBinding
import java.util.*
import kotlin.collections.ArrayList

class CartListAdapter(private val viewModel: CartViewModel):
    ListAdapter<Response.CartProduct, CartListAdapter.CartListViewHolder>(CartListDiffCallback()) {

    override fun submitList(list: MutableList<Response.CartProduct>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartListViewHolder {
        return CartListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CartListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) { holder.bind(viewModel , item)}
    }

    class CartListViewHolder private constructor(private  val binding: CartItemBinding ) : RecyclerView.ViewHolder(binding.root) {

        val id : String = UUID.randomUUID().toString()

        fun bind(viewModel: CartViewModel , item: Response.CartProduct) {
            viewModel.imageHashMap[id] = item.imageUrl
            viewModel.loadImage(item.imageUrl, binding.cartItemImageView, id)

            binding.viewmodel = viewModel
            binding.product = item

            binding.executePendingBindings()
        }

        companion object {
            fun from (parent: ViewGroup) : CartListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CartItemBinding.inflate(layoutInflater , parent , false)
                return CartListViewHolder(binding)
            }
        }
    }
}

class CartListDiffCallback : DiffUtil.ItemCallback<Response.CartProduct>() {

    override fun areContentsTheSame(oldItem: Response.CartProduct, newItem: Response.CartProduct): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Response.CartProduct, newItem: Response.CartProduct): Boolean {
        return oldItem == newItem
    }
}