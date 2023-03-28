package com.webronin_26.online_mart.query_company_product

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.*
import com.webronin_26.online_mart.Event
import com.webronin_26.online_mart.data.remote.Response
import com.webronin_26.online_mart.data.remote.Result
import com.webronin_26.online_mart.data.source.OnlineMartRepository
import kotlinx.coroutines.launch

class QueryCompanyProductViewModel  : ViewModel(), LifecycleEventObserver {

    var repository: OnlineMartRepository? = null

    val products = MutableLiveData<Event<List<Response.QueryCompanyProduct>>>()
    var imageHashMap : HashMap<String , String> = HashMap()
    val queryCompanyProductBundle = MutableLiveData<Event<Bundle>>()

    val queryCompanyProductCompanyId = MutableLiveData<Int>()
    val queryCompanyProductCompanyName = MutableLiveData<String>()
    val queryCompanyProductCompanyAddress = MutableLiveData<String>()

    val queryCompanyProductScrollViewVisible = MutableLiveData<Int>()
    val queryCompanyProductRecyclerViewVisible = MutableLiveData<Int>()
    val queryCompanyProductTextViewVisible = MutableLiveData<Int>()

    /**
     * set a alert button, if an error occur
     */
    val queryCompanyProductAlertButtonVisible = MutableLiveData<Int>()
    val queryCompanyProductAlertButtonText = MutableLiveData<String>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> { refreshQueryCompanyProduct() }
            else -> {}
        }
    }

    fun refreshQueryCompanyProduct() {
        viewModelScope.launch {
            if (queryCompanyProductCompanyId.value != 0) {

                queryCompanyProductAlertButtonVisible.value = View.GONE
                queryCompanyProductRecyclerViewVisible.value = View.GONE
                queryCompanyProductTextViewVisible.value = View.GONE
                queryCompanyProductScrollViewVisible.value = View.GONE

                repository?.queryCompanyProduct(queryCompanyProductCompanyId.value!!).let { result ->
                    when (result) {
                        is Result.Success -> {
                            queryCompanyProductScrollViewVisible.value = View.VISIBLE
                            queryCompanyProductCompanyName.value = result.data.data.companyName
                            queryCompanyProductCompanyAddress.value = result.data.data.companyAddress

                            if (result.data.count != 0) {
                                queryCompanyProductRecyclerViewVisible.value = View.VISIBLE
                                val productList = result.data.data.products.toMutableList()
                                products.value = Event(productList)
                            } else {
                                queryCompanyProductTextViewVisible.value = View.VISIBLE
                            }
                        }
                        is Result.ConnectException -> {
                            queryCompanyProductAlertButtonVisible.value = View.VISIBLE
                            queryCompanyProductAlertButtonText.value = "網路連線異常，請確認網路狀態"
                        }
                        else -> {
                            queryCompanyProductAlertButtonVisible.value = View.VISIBLE
                            queryCompanyProductAlertButtonText.value = "網路錯誤，請稍等"
                        }
                    }
                }
            }
        }
    }

    fun loadImage(urlString: String, imageView: AppCompatImageView, id : String) {
        viewModelScope.launch {
            repository?.getImage(urlString).let { bitmap ->
                if (bitmap != null) {
                    if (imageHashMap[id] == urlString) {
                        imageView.setImageBitmap(bitmap)
                    }
                }
            }
        }
    }
}