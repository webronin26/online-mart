package com.webronin_26.online_mart.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.webronin_26.online_mart.EventObserver
import com.webronin_26.online_mart.OnlineMartApplication
import com.webronin_26.online_mart.R
import com.webronin_26.online_mart.databinding.SearchActivityBinding
import com.webronin_26.online_mart.query_product.QueryProductActivity

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var viewDataBinding: SearchActivityBinding

    private var searchAdapter: SearchAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        viewModel.repository = (applicationContext as OnlineMartApplication).repository
        lifecycle.addObserver(viewModel)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.search_activity)
        viewDataBinding.lifecycleOwner = this
        viewDataBinding.viewmodel = viewModel

        initView()
        initAdapter()
    }

    private fun initView() {

        viewDataBinding.searchCancelButton.setOnClickListener { finish() }

        viewDataBinding.searchStartSearchButton.setOnClickListener {
            setEditTextUnFocus()
            viewModel.searchRecyclerViewVisible.value = View.GONE
            viewModel.searchTextViewVisible.value = View.GONE
            startRequest()
        }

        viewModel.products.observe(this, EventObserver {
            (viewDataBinding.searchRecyclerView.adapter as SearchAdapter).submitList(it.toMutableList())
        })

        viewModel.queryActivityBundle.observe(this, EventObserver { bundle ->
            val intent = Intent(this, QueryProductActivity::class.java)
            intent.putExtra("id", bundle.getInt("id"))
            intent.putExtra("price", bundle.getFloat("price"))
            intent.putExtra("name", bundle.getString("name"))
            startActivity(intent)
        })

        viewDataBinding.searchEditText.setOnClickListener { setEditTextFocus() }

        setEditTextFocus()
    }

    private fun initAdapter() {
        val viewModel = viewDataBinding.viewmodel
        if (viewModel != null) {
            searchAdapter = SearchAdapter(viewModel)
            viewDataBinding.searchRecyclerView.adapter = searchAdapter
            (viewDataBinding.searchRecyclerView.adapter as SearchAdapter).submitList(null)
        }
    }

    private fun startRequest() {
        if (viewDataBinding.searchEditText.text.isNullOrEmpty()) {
            Toast.makeText(this, "請輸入關鍵字", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.sendSearchRequest(viewDataBinding.searchEditText.text.toString())
        }
    }

    private fun setEditTextFocus() {
        viewDataBinding.searchEditText.isFocusableInTouchMode = true
        val methodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        methodManager.showSoftInput(viewDataBinding.searchEditText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun setEditTextUnFocus() {
        val methodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        methodManager.hideSoftInputFromWindow(viewDataBinding.searchEditText.windowToken, 0)
    }
}