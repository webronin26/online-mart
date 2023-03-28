package com.webronin_26.online_mart.search

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.lifecycle.*
import com.webronin_26.online_mart.Event
import com.webronin_26.online_mart.data.remote.Response
import com.webronin_26.online_mart.data.remote.Result
import com.webronin_26.online_mart.data.source.OnlineMartRepository
import kotlinx.coroutines.launch

class SearchViewModel  : ViewModel(), LifecycleEventObserver {

    var repository: OnlineMartRepository? = null

    val searchRecyclerViewVisible = MutableLiveData<Int>()
    val searchTextViewVisible = MutableLiveData<Int>()
    val searchTextViewText = MutableLiveData<String>()

    val products = MutableLiveData<Event<List<Response.SearchProduct>>>()
    var imageHashMap : HashMap<String , String> = HashMap()
    val queryActivityBundle = MutableLiveData<Event<Bundle>>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> { searchTextViewVisible.value = View.GONE }
            else -> {}
        }
    }

    fun sendSearchRequest(searchString: String) {

        searchRecyclerViewVisible.value = View.GONE
        searchTextViewVisible.value = View.GONE
        searchTextViewText.value = ""

        viewModelScope.launch {
            repository?.search(searchString).let { result ->
                when (result) {
                    is Result.Success -> {
                        if (result.data.count != 0) {
                            searchRecyclerViewVisible.value = View.VISIBLE
                            products.value = Event(result.data.data.toMutableList())
                        } else {
                            searchTextViewVisible.value = View.VISIBLE
                            searchTextViewText.value = "找不到產品"
                        }
                    }
                    is Result.ConnectException -> {
                        searchTextViewVisible.value = View.VISIBLE
                        searchTextViewText.value = "網路連線異常，請確認網路狀態"
                    }
                    else -> {
                        searchTextViewVisible.value = View.VISIBLE
                        searchTextViewText.value = "網路錯誤，請稍等"
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