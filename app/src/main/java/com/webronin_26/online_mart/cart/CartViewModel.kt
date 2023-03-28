package com.webronin_26.online_mart.cart

import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.webronin_26.online_mart.Event
import com.webronin_26.online_mart.VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION
import com.webronin_26.online_mart.VIEW_MODEL_INTERNET_ERROR
import com.webronin_26.online_mart.VIEW_MODEL_INTERNET_SUCCESS
import com.webronin_26.online_mart.data.remote.Response
import com.webronin_26.online_mart.data.remote.Result
import com.webronin_26.online_mart.data.source.OnlineMartRepository
import kotlinx.coroutines.launch

class CartViewModel : ViewModel(), LifecycleEventObserver {

    var repository: OnlineMartRepository? = null

    val viewModelInternetStatus = MutableLiveData<Event<Int>>()
    val products = MutableLiveData<Event<List<Response.CartProduct>>>()
    var imageHashMap : HashMap<String , String> = HashMap()

    /**
     * set a alert button, if an error occur
     */
    val alertButtonVisible = MutableLiveData<Int>()
    val alertButtonText = MutableLiveData<String>()

    val cartScrollViewVisible = MutableLiveData<Int>()
    val cartTotalPrice = MutableLiveData<Float>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //
    }

    fun refreshQueryCart(token: String) {

        alertButtonVisible.value = View.GONE
        cartScrollViewVisible.value = View.GONE

        viewModelScope.launch {
            repository?.queryCart(token).let { result ->
                when (result) {
                    is Result.Success -> {
                        if (result.data.count != 0) {
                            try {
                                cartScrollViewVisible.value = View.VISIBLE
                                cartTotalPrice.value = result.data.data.totalPrice

                                val cartProductArrayType = object : TypeToken<Array<Response.CartProduct>>() {}.type
                                val cartProductArray: Array<Response.CartProduct> = Gson().fromJson(result.data.data.productList, cartProductArrayType)
                                val responseProducts =  cartProductArray.toMutableList()

                                products.value = Event(responseProducts)
                            } catch (e: Exception) {
                                alertButtonVisible.value = View.VISIBLE
                                alertButtonText.value = "目前購物車沒有物品"
                            }
                        } else {
                            alertButtonVisible.value = View.VISIBLE
                            alertButtonText.value = "目前購物車沒有物品"
                        }
                    }
                    is Result.ConnectException -> {
                        alertButtonVisible.value = View.VISIBLE
                        alertButtonText.value = "網路連線異常，請確認網路狀態"
                    }
                    else -> {
                        alertButtonVisible.value = View.VISIBLE
                        alertButtonText.value = "網路錯誤，請稍等"
                    }
                }
            }
        }
    }

    fun checkCart(token: String, memberAddress: String) {
        viewModelScope.launch {
            repository?.checkCart(token, memberAddress).let { result ->
                when (result) {
                    is Result.Success ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                    is Result.ConnectException ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION)
                    else ->
                        viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR)
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