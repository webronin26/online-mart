package com.webronin_26.online_mart

import android.app.Application
import com.webronin_26.online_mart.data.RepositoryProvider
import com.webronin_26.online_mart.data.source.OnlineMartRepository

class OnlineMartApplication : Application() {
    val repository : OnlineMartRepository
        get() = RepositoryProvider.provideShoppingRepository()
}