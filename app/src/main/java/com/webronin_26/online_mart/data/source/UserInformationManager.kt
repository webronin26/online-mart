package com.webronin_26.online_mart.data.source

import android.content.Context
import android.content.SharedPreferences

object UserInformationManager {

    private fun getSharedPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences("user", Context.MODE_PRIVATE)

    fun saveUserName(context: Context, name: String) =
        getSharedPreferences(context).edit().putString("name" , name).apply()

    fun getUserName(context: Context): String? =
        getSharedPreferences(context).getString("name","")

    fun saveUserID(context: Context, id: Int) =
        getSharedPreferences(context).edit().putInt("id" , id).apply()

    fun getUserID(context: Context): Int =
        getSharedPreferences(context).getInt("id",0)

    fun saveUserAccount(context: Context, account: String) =
        getSharedPreferences(context).edit().putString("account" , account).apply()

    fun getUserAccount(context: Context): String? =
        getSharedPreferences(context).getString("account","")

    fun saveUserPassword(context: Context, password: String) =
        getSharedPreferences(context).edit().putString("password" , password).apply()

    fun getUserPassword(context: Context): String? =
        getSharedPreferences(context).getString("password","")
}
