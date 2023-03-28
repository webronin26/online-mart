package com.webronin_26.online_mart.query_company_product

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.webronin_26.online_mart.EventObserver
import com.webronin_26.online_mart.OnlineMartApplication
import com.webronin_26.online_mart.R
import com.webronin_26.online_mart.databinding.QueryCompanyProductActivityBinding
import com.webronin_26.online_mart.query_product.QueryProductActivity

class QueryCompanyProductActivity : AppCompatActivity()  {

    private lateinit var viewModel: QueryCompanyProductViewModel
    private lateinit var viewDataBinding: QueryCompanyProductActivityBinding
    private var queryCompanyProductAdapter: QueryCompanyProductAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[QueryCompanyProductViewModel::class.java]
        viewModel.repository = (applicationContext as OnlineMartApplication).repository
        lifecycle.addObserver(viewModel)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.query_company_product_activity)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel

        initView()
    }

    private fun initView() {

        if (intent.getIntExtra("company_id",0) != 0) {

            viewModel.queryCompanyProductCompanyId.value = intent.getIntExtra("company_id",0)

            viewModel.queryCompanyProductRecyclerViewVisible.value = View.VISIBLE
            viewModel.queryCompanyProductTextViewVisible.value = View.VISIBLE
            viewModel.queryCompanyProductScrollViewVisible.value = View.VISIBLE
            viewModel.queryCompanyProductAlertButtonVisible.value = View.VISIBLE

            viewModel.products.observe(this, EventObserver {
                (viewDataBinding.queryCompanyProductRecyclerView.adapter as QueryCompanyProductAdapter).submitList(it.toMutableList())
            })

            viewDataBinding.queryCompanyProductAlertButton.setOnClickListener {
                viewModel.refreshQueryCompanyProduct()
            }

            viewModel.queryCompanyProductBundle.observe(this, EventObserver { bundle ->
                val intent = Intent(this, QueryProductActivity::class.java)
                intent.putExtra("id", bundle.getInt("id"))
                intent.putExtra("price", bundle.getFloat("price"))
                intent.putExtra("name", bundle.getString("name"))
                startActivity(intent)
            })

            val dataBindingViewModel = viewDataBinding.viewmodel
            if (dataBindingViewModel != null) {
                queryCompanyProductAdapter = QueryCompanyProductAdapter(dataBindingViewModel)
                viewDataBinding.queryCompanyProductRecyclerView.adapter = queryCompanyProductAdapter
                (viewDataBinding.queryCompanyProductRecyclerView.adapter as QueryCompanyProductAdapter).submitList(null)
            }
        } else {
            viewModel.queryCompanyProductRecyclerViewVisible.value = View.GONE
            viewModel.queryCompanyProductTextViewVisible.value = View.GONE
            viewModel.queryCompanyProductScrollViewVisible.value = View.GONE

            viewModel.queryCompanyProductAlertButtonVisible.value = View.VISIBLE
            viewModel.queryCompanyProductAlertButtonText.value = "目前廠商沒有其他商品上架"
        }
    }
}