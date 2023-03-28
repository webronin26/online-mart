package com.webronin_26.online_mart

import androidx.lifecycle.Observer

const val VIEW_MODEL_INTERNET_NOT_INITIALIZE = 999
const val VIEW_MODEL_INTERNET_SUCCESS = 100
const val VIEW_MODEL_INTERNET_SUCCESS_BUT_EMPTY = 101
const val VIEW_MODEL_INTERNET_ERROR = 200
const val VIEW_MODEL_INTERNET_ERROR_INVENTORY_NUMBER = 201
const val VIEW_MODEL_INTERNET_ERROR_PRODUCT_COMPANY = 202
const val VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION = 300

open class Event<out T>(private val content: T) {

    @Suppress("MemberVisibilityCanBePrivate")
    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T>> {
    override fun onChanged(event: Event<T>?) {
        event?.getContentIfNotHandled()?.let {
            onEventUnhandledContent(it)
        }
    }
}