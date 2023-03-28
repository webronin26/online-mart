package com.webronin_26.online_mart.query_company_product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webronin_26.online_mart.data.remote.Response
import com.webronin_26.online_mart.Event
import com.webronin_26.online_mart.databinding.QueryCompanyProductItemBinding
import java.util.*
import kotlin.collections.ArrayList

class QueryCompanyProductAdapter (private val viewModel: QueryCompanyProductViewModel):
    ListAdapter<Response.QueryCompanyProduct, QueryCompanyProductAdapter.QueryCompanyProductViewHolder>(QueryCompanyProductDiffCallback()) {

    override fun submitList(list: MutableList<Response.QueryCompanyProduct>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryCompanyProductViewHolder {
        return QueryCompanyProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: QueryCompanyProductViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) { holder.bind(viewModel , item)}
    }

    class QueryCompanyProductViewHolder private constructor(private  val binding: QueryCompanyProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val id : String = UUID.randomUUID().toString()

        fun bind(viewModel: QueryCompanyProductViewModel, item: Response.QueryCompanyProduct) {
            viewModel.imageHashMap[id] = item.imageUrl
            viewModel.loadImage(item.imageUrl, binding.queryCompanyProductItemImageView, id)

            binding.viewmodel = viewModel
            binding.product = item
            binding.queryCompanyProductItemLinearLayout.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("id" , item.id)
                bundle.putFloat("price", item.price)
                bundle.putString("name", item.name)
                viewModel.queryCompanyProductBundle.value = Event(bundle)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from (parent: ViewGroup) : QueryCompanyProductAdapter.QueryCompanyProductViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = QueryCompanyProductItemBinding.inflate(layoutInflater , parent , false)
                return QueryCompanyProductAdapter.QueryCompanyProductViewHolder(binding)
            }
        }
    }
}

class QueryCompanyProductDiffCallback : DiffUtil.ItemCallback<Response.QueryCompanyProduct>() {

    override fun areContentsTheSame(oldItem: Response.QueryCompanyProduct, newItem: Response.QueryCompanyProduct): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Response.QueryCompanyProduct, newItem: Response.QueryCompanyProduct): Boolean {
        return oldItem == newItem
    }
}