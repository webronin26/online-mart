package com.webronin_26.online_mart.profile

import androidx.lifecycle.*
import com.webronin_26.online_mart.Event
import com.webronin_26.online_mart.VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION
import com.webronin_26.online_mart.VIEW_MODEL_INTERNET_ERROR
import com.webronin_26.online_mart.VIEW_MODEL_INTERNET_SUCCESS
import com.webronin_26.online_mart.data.source.OnlineMartRepository
import com.webronin_26.online_mart.data.remote.Result
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel(), LifecycleEventObserver {

    var repository: OnlineMartRepository? = null

    val profileLinearLayoutVisible = MutableLiveData<Int>()
    val loginButtonVisible = MutableLiveData<Int>()

    val profileUserId = MutableLiveData<Int>()
    val profileUserName = MutableLiveData<String>()
    val profileUserAccount = MutableLiveData<String>()

    val viewModelInternetStatus = MutableLiveData<Event<Int>>()

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //
    }

    fun logout(token: String) {
        viewModelScope.launch {
            repository?.logout(token).let { result ->
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
}