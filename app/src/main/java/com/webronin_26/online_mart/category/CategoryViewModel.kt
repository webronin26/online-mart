package com.webronin_26.online_mart.category

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.webronin_26.online_mart.data.source.OnlineMartRepository

class CategoryViewModel : ViewModel(), LifecycleEventObserver {

    var repository: OnlineMartRepository? = null

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        //
    }
}