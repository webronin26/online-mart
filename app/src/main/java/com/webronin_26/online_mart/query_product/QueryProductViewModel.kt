package com.webronin_26.online_mart.query_product

import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import androidx.lifecycle.*
import com.webronin_26.online_mart.*
import com.webronin_26.online_mart.data.remote.Result
import com.webronin_26.online_mart.data.source.OnlineMartRepository
import kotlinx.coroutines.launch

class QueryProductViewModel  : ViewModel(), LifecycleEventObserver {

    var repository: OnlineMartRepository? = null
    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    val currentBuyNumber = MutableLiveData<Int>()

    val productId = MutableLiveData<Int>()
    val productPicture = MutableLiveData<Bitmap>()
    val productName = MutableLiveData<String>()
    val productPrice = MutableLiveData<Float>()
    val productNumber = MutableLiveData<Int>()
    var productMaxBuy = MutableLiveData<Int>()
    val productSummary = MutableLiveData<String>()
    val productInformation = MutableLiveData<String>()

    val productCompanyID = MutableLiveData<Int>()
    val productCompanyName = MutableLiveData<String>()

    /**
     * set a alert button, if an error occur
     */
    val alertButtonVisible = MutableLiveData<Int>()
    val alertButtonText = MutableLiveData<String>()

    val queryProductLinearLayoutVisible = MutableLiveData<Int>()
    val queryProductControlLinearLayoutVisible = MutableLiveData<Int>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> { refreshQueryProductActivity() }
            else -> {}
        }
    }

    fun setData(intent: Intent) {
        productId.value = intent.getIntExtra("id",0)
        productName.value = intent.getStringExtra("name")
        productPrice.value = intent.getFloatExtra("price", 0f)

        currentBuyNumber.value = 0
        productNumber.value = 0
        productMaxBuy.value = 0
    }

    fun refreshQueryProductActivity() {

        if (productId.value != null && productId.value != 0) {
            viewModelScope.launch {
                repository?.queryProduct(productId.value!!).let { result ->
                    when (result) {
                        is Result.Success -> {
                            alertButtonVisible.value = View.GONE
                            queryProductLinearLayoutVisible.value = View.VISIBLE
                            queryProductControlLinearLayoutVisible.value = View.VISIBLE

                            productId.value = result.data.data.id
                            productName.value = result.data.data.name
                            productPrice.value = result.data.data.price
                            productNumber.value = result.data.data.inventoryNumber
                            productMaxBuy.value = result.data.data.maxBuy
                            productSummary.value = result.data.data.summary
                            productInformation.value = result.data.data.information

                            setImage(result.data.data.imageUrl, productPicture)

                            productCompanyID.value = result.data.data.companyId
                            productCompanyName.value = result.data.data.companyName

                            currentBuyNumber.value = 0
                        }
                        is Result.ConnectException -> {
                            queryProductLinearLayoutVisible.value = View.GONE
                            queryProductControlLinearLayoutVisible.value = View.GONE
                            alertButtonVisible.value = View.VISIBLE
                            alertButtonText.value = "網路連線異常，請確認網路狀態"
                        }
                        else -> {
                            queryProductLinearLayoutVisible.value = View.GONE
                            queryProductControlLinearLayoutVisible.value = View.GONE
                            alertButtonVisible.value = View.VISIBLE
                            alertButtonText.value = "網路錯誤，請稍等"
                        }
                    }
                }
            }
        } else {
            queryProductLinearLayoutVisible.value = View.GONE
            queryProductControlLinearLayoutVisible.value = View.GONE
            alertButtonVisible.value = View.VISIBLE
            alertButtonText.value = "目前相關商品尚未上架"
        }
    }

    private fun setImage(urlString: String?, liveData: MutableLiveData<Bitmap>) {
        if (urlString != null) {
            viewModelScope.launch {
                repository?.getImage(urlString).let { bitmap ->
                    liveData.value = bitmap
                }
            }
        }
    }

    fun plusButtonClick() {
        if (productMaxBuy.value!! > currentBuyNumber.value!!) {
            currentBuyNumber.value = currentBuyNumber.value!! + 1
        }
    }

    fun minusButtonClick() {
        if (currentBuyNumber.value!! > 0) {
            currentBuyNumber.value = currentBuyNumber.value!! - 1
        }
    }

    fun productAddToCart(token: String) {
        if (currentBuyNumber.value != 0) {
            viewModelScope.launch {
                repository?.addProductToCart(token, productId.value!!, currentBuyNumber.value!!).let { result ->
                    when (result) {
                        is Result.Success -> {
                            viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_SUCCESS)
                        }
                        is Result.InventoryNumberError -> {
                            viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR_INVENTORY_NUMBER)
                        }
                        is Result.ProductCompanyError -> {
                            viewModelInternetStatus.value = Event(VIEW_MODEL_INTERNET_ERROR_PRODUCT_COMPANY)
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