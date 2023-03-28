package com.webronin_26.online_mart.query_product

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.webronin_26.online_mart.*
import com.webronin_26.online_mart.data.source.TokenManager
import com.webronin_26.online_mart.databinding.QueryProductActivityBinding
import com.webronin_26.online_mart.login.LoginActivity
import com.webronin_26.online_mart.query_company_product.QueryCompanyProductActivity

class QueryProductActivity : AppCompatActivity() {

    private lateinit var viewModel: QueryProductViewModel
    private lateinit var viewDataBinding: QueryProductActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[QueryProductViewModel::class.java]
        viewModel.repository = (applicationContext as OnlineMartApplication).repository
        lifecycle.addObserver(viewModel)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.query_product_activity)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel

        viewModel.setData(intent)

        initView()
    }

    private fun initView() {

        if (viewModel.productCompanyID.value != 0) {

            viewDataBinding.queryProductAlertButton.setOnClickListener { viewModel.refreshQueryProductActivity() }

            viewDataBinding.queryProductPlusButton.setOnClickListener { viewModel.plusButtonClick() }

            viewDataBinding.queryProductMinusButton.setOnClickListener { viewModel.minusButtonClick() }

            viewDataBinding.queryCompanyButton.setOnClickListener {
                val intent = Intent(this, QueryCompanyProductActivity::class.java)
                intent.putExtra("company_id", viewModel.productCompanyID.value!!)
                startActivity(intent)
            }

            // 放入購物車的邏輯
            viewDataBinding.queryProductAddCartButton.setOnClickListener {
                if (viewModel.productId.value == 0) {
                    Toast.makeText(this, "產品尚未開放購買" , Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }

                if (TokenManager.getToken(this) == "") {
                    startActivity(Intent(this, LoginActivity::class.java))
                } else {
                    viewModel.productAddToCart(TokenManager.getToken(this)!!)
                }
            }

            // 監聽購物車的回覆狀態
            viewModel.viewModelInternetStatus.observe(this, EventObserver {
                when(it) {
                    VIEW_MODEL_INTERNET_SUCCESS -> {
                        Toast.makeText(this, "加入購物車成功" , Toast.LENGTH_SHORT).show()
                        viewModel.currentBuyNumber.value = 0
                        viewModel.refreshQueryProductActivity()
                    }
                    VIEW_MODEL_INTERNET_ERROR_INVENTORY_NUMBER -> Toast.makeText(this, "商品庫存不足" , Toast.LENGTH_SHORT).show()
                    VIEW_MODEL_INTERNET_ERROR_PRODUCT_COMPANY -> Toast.makeText(this, "商品廠商跟購物車廠商不同" , Toast.LENGTH_SHORT).show()
                    VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION -> Toast.makeText(this, "網路連線異常，請確認網路狀態" , Toast.LENGTH_SHORT).show()
                    VIEW_MODEL_INTERNET_ERROR -> Toast.makeText(this, "網路錯誤，請稍等" , Toast.LENGTH_SHORT).show()
                }
            })
        }

        setSupportActionBar(viewDataBinding.queryToolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        super.onBackPressed()
    }
}