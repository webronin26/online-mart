package com.webronin_26.online_mart.query_order

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.webronin_26.online_mart.*
import com.webronin_26.online_mart.data.source.TokenManager
import com.webronin_26.online_mart.databinding.QueryOrderFragmentBinding
import com.webronin_26.online_mart.login.LoginActivity

class QueryOrderFragment : Fragment() {

    private lateinit var viewModel: QueryOrderViewModel
    private lateinit var viewDataBinding: QueryOrderFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.query_order_fragment, container, false)

        viewModel = ViewModelProvider(this)[QueryOrderViewModel::class.java]
        viewModel.repository = (requireContext().applicationContext as OnlineMartApplication).repository
        lifecycle.addObserver(viewModel)

        viewDataBinding = QueryOrderFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        viewModel.orderId.value = arguments?.getInt("order_id") ?: 0
        viewModel.orderTextViewNumber.value = arguments?.getString("order_number") ?: ""
        viewModel.orderTextViewProductList.value = arguments?.getString("product_list") ?: ""
        viewModel.orderTextViewPrice.value = arguments?.getFloat("total_price") ?: 0f

        return view
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {

        if (TokenManager.getToken(requireContext()) != "") {

            if (viewModel.orderId.value != 0) {

                viewDataBinding.queryOrderFragmentCancelButton.setOnClickListener {
                    findNavController().navigate(R.id.action_query_order_fragment_to_query_order_list_fragment)
                }

                viewModel.viewModelInternetStatus.observe(this, EventObserver {
                    when(it) {
                        VIEW_MODEL_INTERNET_SUCCESS -> {}
                        VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION ->
                            Toast.makeText(requireContext(), "網路連線異常，請確認網路狀態", Toast.LENGTH_LONG).show()
                        VIEW_MODEL_INTERNET_ERROR ->
                            Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
                    }}
                )

                viewModel.refreshQueryOrder(TokenManager.getToken(requireContext())!!)

            } else {
                Toast.makeText(requireContext(), "目前此訂單無詳細資料", Toast.LENGTH_LONG).show()
            }
        } else {
            startActivity(Intent(activity, LoginActivity::class.java))
        }
    }
}
