package com.webronin_26.online_mart.data

import com.webronin_26.online_mart.data.source.OnlineMartRepository

object RepositoryProvider {

    @Volatile
    var onlineMartRepository : OnlineMartRepository? = null

    fun provideShoppingRepository(): OnlineMartRepository {
        synchronized(this) {
            return  onlineMartRepository ?: onlineMartRepository ?: createShoppingRepository()
        }
    }

    private fun createShoppingRepository(): OnlineMartRepository = OnlineMartRepository()
}
