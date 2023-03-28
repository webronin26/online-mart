package com.webronin_26.online_mart.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.lifecycle.lifecycleScope
import com.webronin_26.online_mart.*
import com.webronin_26.online_mart.data.remote.Result
import com.webronin_26.online_mart.data.source.OnlineMartRepository
import com.webronin_26.online_mart.data.source.TokenManager
import com.webronin_26.online_mart.data.source.UserInformationManager
import com.webronin_26.online_mart.databinding.LoginActivityBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private var repository: OnlineMartRepository? = null
    private lateinit var viewDataBinding: LoginActivityBinding

    val accountEditText = ObservableField<String>()
    val passwordEditText = ObservableField<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewDataBinding = DataBindingUtil.setContentView(this, R.layout.login_activity)
        viewDataBinding.loginActivity = this
        repository = (applicationContext as OnlineMartApplication).repository
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {

        if (UserInformationManager.getUserAccount(this) != "" && UserInformationManager.getUserPassword(this) != "") {
            accountEditText.set(UserInformationManager.getUserAccount(this))
            passwordEditText.set(UserInformationManager.getUserPassword(this))
        }

        viewDataBinding.loginButton.setOnClickListener {
            if (accountEditText.get().isNullOrEmpty() || passwordEditText.get().isNullOrEmpty()) {
                Toast.makeText(this, "請將資料輸入完整",Toast.LENGTH_LONG).show()
            } else {
                lifecycleScope.launch {
                    repository?.login(accountEditText.get().toString(), passwordEditText.get().toString()).let { result ->
                        when (result) {
                            is Result.Success -> {
                                setLogin(
                                    token = result.data.data.token,
                                    id = result.data.data.id,
                                    name = result.data.data.name,
                                    account = accountEditText.get().toString(),
                                    passWord = passwordEditText.get().toString()
                                )
                            }
                            is Result.ConnectException -> setLoginConnectError()
                            else -> setLoginError()
                        }
                    }
                }
            }
        }
    }

    private fun setLogin(token: String, id: Int, name: String, account: String, passWord: String) {
        TokenManager.setToken(this, token)
        UserInformationManager.saveUserID(this, id)
        UserInformationManager.saveUserName(this, name)
        UserInformationManager.saveUserAccount(this, account)
        UserInformationManager.saveUserPassword(this, passWord)

        finish()
    }

    private fun setLoginConnectError() { Toast.makeText(this, "網路連線異常，請確認網路狀態" , Toast.LENGTH_LONG).show() }

    private fun setLoginError() { Toast.makeText(this, "帳號密碼有誤" , Toast.LENGTH_LONG).show() }
}
