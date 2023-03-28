package com.webronin_26.online_mart.query_order_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.webronin_26.online_mart.data.remote.Response
import com.webronin_26.online_mart.Event
import com.webronin_26.online_mart.databinding.QueryOrderListItemBinding
import kotlin.collections.ArrayList

class QueryOrderListAdapter (private val viewModel: QueryOrderListViewModel):
    ListAdapter<Response.QueryOrderList, QueryOrderListAdapter.QueryOrderListViewHolder>(QueryOrderListDiffCallback()) {

    override fun submitList(list: MutableList<Response.QueryOrderList>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueryOrderListViewHolder {
        return QueryOrderListViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: QueryOrderListViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) { holder.bind(viewModel , item)}
    }

    class QueryOrderListViewHolder private constructor(private  val binding: QueryOrderListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: QueryOrderListViewModel , item: Response.QueryOrderList) {
            binding.viewmodel = viewModel
            binding.order = item
            binding.queryOrderListItemLinearLayout.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("order_id" , item.id)
                bundle.putString("order_number" , item.orderNumber)
                bundle.putString("product_list" , item.productList)
                bundle.putFloat("total_price" , item.totalPrice)
                viewModel.queryOrderListFragmentBundle.value = Event(bundle)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from (parent: ViewGroup) : QueryOrderListViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = QueryOrderListItemBinding.inflate(layoutInflater , parent , false)
                return QueryOrderListViewHolder(binding)
            }
        }
    }
}

class QueryOrderListDiffCallback : DiffUtil.ItemCallback<Response.QueryOrderList>() {

    override fun areContentsTheSame(oldItem: Response.QueryOrderList, newItem: Response.QueryOrderList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areItemsTheSame(oldItem: Response.QueryOrderList, newItem: Response.QueryOrderList): Boolean {
        return oldItem == newItem
    }
}