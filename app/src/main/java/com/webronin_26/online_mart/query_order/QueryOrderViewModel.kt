package com.webronin_26.online_mart.query_order

import androidx.lifecycle.*
import com.webronin_26.online_mart.Event
import com.webronin_26.online_mart.VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION
import com.webronin_26.online_mart.VIEW_MODEL_INTERNET_ERROR
import com.webronin_26.online_mart.data.remote.Result
import com.webronin_26.online_mart.data.source.OnlineMartRepository
import kotlinx.coroutines.launch

class QueryOrderViewModel  : ViewModel(), LifecycleEventObserver {

    var repository: OnlineMartRepository? = null
    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    val orderId = MutableLiveData<Int>()
    val orderTextViewNumber = MutableLiveData<String>()
    val orderTextViewAddress = MutableLiveData<String>()
    val orderTextViewProductList = MutableLiveData<String>()
    val orderTextViewPrice = MutableLiveData<Float>()
    val orderTextViewPaidTime = MutableLiveData<String>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //
    }

    fun refreshQueryOrder(token: String) {
        orderId.value?.let { orderId ->
            viewModelScope.launch {
                repository?.queryOrder(token, orderId).let { result ->
                    when (result) {
                        is Result.Success -> {
                            orderTextViewNumber.value = result.data.data.orderNumber
                            orderTextViewAddress.value = result.data.data.orderAddress
                            orderTextViewProductList.value = result.data.data.productList
                            orderTextViewPrice.value = result.data.data.totalPrice
                            orderTextViewPaidTime.value = result.data.data.paidTime
                        }
                        is Result.ConnectException -> {
                            viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION)
                        }
                        else -> {
                            viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
                        }
                    }
                }
            }
        }
    }
}