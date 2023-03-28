package com.webronin_26.online_mart.query_order_list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.*
import com.webronin_26.online_mart.Event
import com.webronin_26.online_mart.data.remote.Response
import com.webronin_26.online_mart.data.remote.Result
import com.webronin_26.online_mart.data.source.OnlineMartRepository
import kotlinx.coroutines.launch

class QueryOrderListViewModel  : ViewModel(), LifecycleEventObserver {

    var repository: OnlineMartRepository? = null

    val orders = MutableLiveData<Event<List<Response.QueryOrderList>>>()
    val queryOrderListFragmentBundle = MutableLiveData<Event<Bundle>>()

    val queryOrderListFragmentScrollViewVisible = MutableLiveData<Int>()
    val queryOrderListRecyclerViewVisible = MutableLiveData<Int>()
    val queryOrderListTextViewVisible = MutableLiveData<Int>()

    /**
     * set a alert button, if an error occur
     */
    val queryOrderListAlertButtonVisible = MutableLiveData<Int>()
    val queryOrderListAlertButtonText = MutableLiveData<String>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //
    }

    fun refreshQueryOrderList(token: String) {

        queryOrderListFragmentScrollViewVisible.value = View.GONE
        queryOrderListRecyclerViewVisible.value = View.GONE
        queryOrderListAlertButtonVisible.value = View.GONE
        queryOrderListTextViewVisible.value = View.GONE

        viewModelScope.launch {
            repository?.queryOrderList(token).let { result ->
                when (result) {
                    is Result.Success -> {
                        queryOrderListFragmentScrollViewVisible.value = View.VISIBLE
                        if (result.data.count != 0) {
                            val orderList =  result.data.data.toMutableList()
                            queryOrderListRecyclerViewVisible.value = View.VISIBLE
                            orders.value = Event(orderList)
                        } else {
                            queryOrderListTextViewVisible.value = View.VISIBLE
                        }
                    }
                    is Result.RecordNotFound -> {
                        queryOrderListFragmentScrollViewVisible.value = View.VISIBLE
                        queryOrderListTextViewVisible.value = View.VISIBLE
                    }
                    is Result.ConnectException -> {
                        queryOrderListAlertButtonVisible.value = View.VISIBLE
                        queryOrderListAlertButtonText.value = "網路連線異常，請確認網路狀態"
                    }
                    else -> {
                        queryOrderListAlertButtonVisible.value = View.VISIBLE
                        queryOrderListAlertButtonText.value = "網路錯誤，請稍等"
                    }
                }
            }
        }
    }
}