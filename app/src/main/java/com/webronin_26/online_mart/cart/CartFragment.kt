package com.webronin_26.online_mart.cart

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
import com.webronin_26.online_mart.databinding.CartFragmentBinding
import com.webronin_26.online_mart.login.LoginActivity

class CartFragment : Fragment() {

    private lateinit var viewModel: CartViewModel
    private lateinit var viewDataBinding: CartFragmentBinding

    private var cartListAdapter: CartListAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.cart_fragment, container, false)

        viewModel = ViewModelProvider(this)[CartViewModel::class.java]
        viewModel.repository = (requireContext().applicationContext as OnlineMartApplication).repository
        lifecycle.addObserver(viewModel)

        viewDataBinding = CartFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {

        if (TokenManager.getToken(requireContext()) != "") {

            viewModel.cartScrollViewVisible.value = View.VISIBLE
            viewModel.alertButtonVisible.value = View.GONE

            viewDataBinding.cartPaidButton.setOnClickListener {
                if (viewDataBinding.cartMemberAddressEditText.text.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), "請輸入收件人地址" , Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.checkCart(TokenManager.getToken(requireContext())!!, viewDataBinding.cartMemberAddressEditText.text.toString())
                }
            }

            viewModel.products.observe(this, EventObserver {
                (viewDataBinding.cartFragmentProductListRecyclerView.adapter as CartListAdapter ).submitList(it.toMutableList())
            })

            viewDataBinding.cartFragmentAlertButton.setOnClickListener {
                viewModel.refreshQueryCart(TokenManager.getToken(requireContext())!!)
            }

            viewModel.viewModelInternetStatus.observe(this, EventObserver {
                when(it) {
                    VIEW_MODEL_INTERNET_SUCCESS ->
                        findNavController().navigate(R.id.action_cart_fragment_to_cart_success_fragment)
                    VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION ->
                        Toast.makeText(requireContext(), "網路連線異常，請確認網路狀態", Toast.LENGTH_LONG).show()
                    VIEW_MODEL_INTERNET_ERROR ->
                        Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
                }
            })

            val dataBindingViewModel = viewDataBinding.viewmodel
            if (dataBindingViewModel != null) {
                cartListAdapter = CartListAdapter(dataBindingViewModel)
                viewDataBinding.cartFragmentProductListRecyclerView.adapter = cartListAdapter
                (viewDataBinding.cartFragmentProductListRecyclerView.adapter as CartListAdapter).submitList(null)
            }

            viewModel.refreshQueryCart(TokenManager.getToken(requireContext())!!)

        } else {

            viewModel.cartScrollViewVisible.value = View.GONE
            viewModel.alertButtonVisible.value = View.VISIBLE
            viewModel.alertButtonText.value = "請先登入會員"

            viewDataBinding.cartFragmentAlertButton.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }
    }
}