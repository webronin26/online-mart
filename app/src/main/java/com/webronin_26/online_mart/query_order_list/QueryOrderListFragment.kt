package com.webronin_26.online_mart.query_order_list

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.webronin_26.online_mart.EventObserver
import com.webronin_26.online_mart.OnlineMartApplication
import com.webronin_26.online_mart.R
import com.webronin_26.online_mart.data.source.TokenManager
import com.webronin_26.online_mart.databinding.QueryOrderListFragmentBinding
import com.webronin_26.online_mart.login.LoginActivity

class QueryOrderListFragment : Fragment() {

    private lateinit var viewModel: QueryOrderListViewModel
    private lateinit var viewDataBinding: QueryOrderListFragmentBinding
    private var queryOrderListAdapter: QueryOrderListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.query_order_list_fragment, container, false)

        viewModel = ViewModelProvider(this)[QueryOrderListViewModel::class.java]
        viewModel.repository = (requireContext().applicationContext as OnlineMartApplication).repository
        lifecycle.addObserver(viewModel)

        viewDataBinding = QueryOrderListFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {

        if (TokenManager.getToken(requireContext()) != "") {

            viewModel.queryOrderListAlertButtonVisible.value = View.VISIBLE
            viewModel.queryOrderListFragmentScrollViewVisible.value = View.VISIBLE
            viewModel.queryOrderListRecyclerViewVisible.value = View.VISIBLE
            viewModel.queryOrderListTextViewVisible.value = View.VISIBLE

            viewModel.orders.observe(this, EventObserver {
                (viewDataBinding.queryOrderListRecyclerView.adapter as QueryOrderListAdapter).submitList(it.toMutableList())
            })

            viewModel.queryOrderListFragmentBundle.observe(this, EventObserver { bundle ->
                findNavController().navigate(R.id.action_query_order_list_fragment_to_query_order_fragment, bundle)
            })

            viewDataBinding.queryOrderListAlertButton.setOnClickListener {
                viewModel.refreshQueryOrderList(TokenManager.getToken(requireContext())!!)
            }

            val dataBindingViewModel = viewDataBinding.viewmodel
            if (dataBindingViewModel != null) {
                queryOrderListAdapter = QueryOrderListAdapter(dataBindingViewModel)
                viewDataBinding.queryOrderListRecyclerView.adapter = queryOrderListAdapter
                (viewDataBinding.queryOrderListRecyclerView.adapter as QueryOrderListAdapter).submitList(null)
            }

            viewModel.refreshQueryOrderList(TokenManager.getToken(requireContext())!!)

        } else {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }
}