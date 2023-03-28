package com.webronin_26.online_mart.main

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.webronin_26.online_mart.*
import com.webronin_26.online_mart.databinding.MainFragmentBinding
import com.webronin_26.online_mart.query_product.QueryProductActivity
import com.webronin_26.online_mart.search.SearchActivity

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var viewDataBinding: MainFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.main_fragment, container, false)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.repository = (requireContext().applicationContext as OnlineMartApplication).repository
        lifecycle.addObserver(viewModel)

        viewDataBinding = MainFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }

    override fun onResume() {
        super.onResume()

        initView()
        viewModel.refreshMainPage()
    }

    private fun initView() {

        viewModel.viewModelInternetStatus.observe(this, EventObserver {
            when(it) {
                VIEW_MODEL_INTERNET_SUCCESS ->
                    Toast.makeText(requireContext(), "更新成功", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION -> {
                    setNullData()
                    Toast.makeText(requireContext(), "網路連線異常，請確認網路狀態", Toast.LENGTH_LONG).show()
                }
                VIEW_MODEL_INTERNET_ERROR -> {
                    setNullData()
                    Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
                }
            }
        })

        viewDataBinding.mainSwipeRefreshLayout.setOnRefreshListener {
            viewDataBinding.mainSwipeRefreshLayout.isRefreshing = false
            viewModel.refreshMainPage()
        }

        viewDataBinding.mainToolBarSearchLinearLayout.setOnClickListener {
            startActivity(Intent(activity, SearchActivity::class.java))
        }

        viewDataBinding.mainToolBarShoppingCartButton.setOnClickListener {
             findNavController().navigate(R.id.action_main_fragment_to_cart_fragment)
        }

        viewDataBinding.mainHotSales01Layout.setOnClickListener {
            if (viewModel.hotSalesId01.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.hotSalesId01.value)
                intent.putExtra("price", viewModel.hotSalesPrice01.value)
                intent.putExtra("name", viewModel.hotSalesText01.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainHotSales02Layout.setOnClickListener {
            if (viewModel.hotSalesId02.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.hotSalesId02.value)
                intent.putExtra("price", viewModel.hotSalesPrice02.value)
                intent.putExtra("name", viewModel.hotSalesText02.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainHotSales03Layout.setOnClickListener {
            if (viewModel.hotSalesId03.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.hotSalesId03.value)
                intent.putExtra("price", viewModel.hotSalesPrice03.value)
                intent.putExtra("name", viewModel.hotSalesText03.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainHotSales04Layout.setOnClickListener {
            if (viewModel.hotSalesId04.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.hotSalesId04.value)
                intent.putExtra("price", viewModel.hotSalesPrice04.value)
                intent.putExtra("name", viewModel.hotSalesText04.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainLatest01Layout.setOnClickListener {
            if (viewModel.latestId01.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.latestId01.value)
                intent.putExtra("price", viewModel.latestPrice01.value)
                intent.putExtra("name", viewModel.latestText01.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainLatest02Layout.setOnClickListener {
            if (viewModel.latestId02.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.latestId02.value)
                intent.putExtra("price", viewModel.latestPrice02.value)
                intent.putExtra("name", viewModel.latestText02.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainLatest03Layout.setOnClickListener {
            if (viewModel.latestId03.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.latestId03.value)
                intent.putExtra("price", viewModel.latestPrice03.value)
                intent.putExtra("name", viewModel.latestText03.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainLatest04Layout.setOnClickListener {
            if (viewModel.latestId04.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.latestId04.value)
                intent.putExtra("price", viewModel.latestPrice04.value)
                intent.putExtra("name", viewModel.latestText04.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainDailyRecommended01Layout.setOnClickListener {
            if (viewModel.dailyRecommendedId01.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.dailyRecommendedId01.value)
                intent.putExtra("price", viewModel.dailyRecommendedPrice01.value)
                intent.putExtra("name", viewModel.dailyRecommendedText01.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainDailyRecommended02Layout.setOnClickListener {
            if (viewModel.dailyRecommendedId02.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.dailyRecommendedId02.value)
                intent.putExtra("price", viewModel.dailyRecommendedPrice02.value)
                intent.putExtra("name", viewModel.dailyRecommendedText02.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainDailyRecommended03Layout.setOnClickListener {
            if (viewModel.dailyRecommendedId03.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.dailyRecommendedId03.value)
                intent.putExtra("price", viewModel.dailyRecommendedPrice03.value)
                intent.putExtra("name", viewModel.dailyRecommendedText03.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainDailyRecommended04Layout.setOnClickListener {
            if (viewModel.dailyRecommendedId04.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.dailyRecommendedId04.value)
                intent.putExtra("price", viewModel.dailyRecommendedPrice04.value)
                intent.putExtra("name", viewModel.dailyRecommendedText04.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainMayLike01Layout.setOnClickListener {
            if (viewModel.mayLikeId01.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.mayLikeId01.value)
                intent.putExtra("price", viewModel.mayLikePrice01.value)
                intent.putExtra("name", viewModel.mayLikeText01.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainMayLike02Layout.setOnClickListener {
            if (viewModel.mayLikeId02.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.mayLikeId02.value)
                intent.putExtra("price", viewModel.mayLikePrice02.value)
                intent.putExtra("name", viewModel.mayLikeText02.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainMayLike03Layout.setOnClickListener {
            if (viewModel.mayLikeId03.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.mayLikeId03.value)
                intent.putExtra("price", viewModel.mayLikePrice03.value)
                intent.putExtra("name", viewModel.mayLikeText03.value)
                startActivity(intent)
            }
        }

        viewDataBinding.mainMayLike04Layout.setOnClickListener {
            if (viewModel.mayLikeId04.value != 0) {
                val intent = Intent(context, QueryProductActivity::class.java)
                intent.putExtra("id", viewModel.mayLikeId04.value)
                intent.putExtra("price", viewModel.mayLikePrice04.value)
                intent.putExtra("name", viewModel.mayLikeText04.value)
                startActivity(intent)
            }
        }
    }

    private fun setNullData() {

        val defaultImageBitmap = BitmapFactory.decodeResource(requireContext().resources, R.drawable.default_product_image)

        viewDataBinding.hotSalesImage01.setImageBitmap(defaultImageBitmap)
        viewDataBinding.hotSalesImage02.setImageBitmap(defaultImageBitmap)
        viewDataBinding.hotSalesImage03.setImageBitmap(defaultImageBitmap)
        viewDataBinding.hotSalesImage04.setImageBitmap(defaultImageBitmap)

        viewDataBinding.latestImage01.setImageBitmap(defaultImageBitmap)
        viewDataBinding.latestImage02.setImageBitmap(defaultImageBitmap)
        viewDataBinding.latestImage03.setImageBitmap(defaultImageBitmap)
        viewDataBinding.latestImage04.setImageBitmap(defaultImageBitmap)

        viewDataBinding.dailyRecommendedImage01.setImageBitmap(defaultImageBitmap)
        viewDataBinding.dailyRecommendedImage02.setImageBitmap(defaultImageBitmap)
        viewDataBinding.dailyRecommendedImage03.setImageBitmap(defaultImageBitmap)
        viewDataBinding.dailyRecommendedImage04.setImageBitmap(defaultImageBitmap)

        viewDataBinding.mayLikeImage01.setImageBitmap(defaultImageBitmap)
        viewDataBinding.mayLikeImage02.setImageBitmap(defaultImageBitmap)
        viewDataBinding.mayLikeImage03.setImageBitmap(defaultImageBitmap)
        viewDataBinding.mayLikeImage04.setImageBitmap(defaultImageBitmap)
    }
}