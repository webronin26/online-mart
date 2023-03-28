package com.webronin_26.online_mart.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.webronin_26.online_mart.*
import com.webronin_26.online_mart.data.source.TokenManager
import com.webronin_26.online_mart.data.source.UserInformationManager
import com.webronin_26.online_mart.databinding.ProfileFragmentBinding
import com.webronin_26.online_mart.login.LoginActivity

class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var viewDataBinding: ProfileFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.profile_fragment, container, false)

        viewModel = ViewModelProvider(this)[ProfileViewModel::class.java]
        viewModel.repository = (requireContext().applicationContext as OnlineMartApplication).repository
        lifecycle.addObserver(viewModel)

        viewDataBinding = ProfileFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {

        if (UserInformationManager.getUserID(requireContext()) != 0 &&
            UserInformationManager.getUserName(requireContext()) != "" &&
            UserInformationManager.getUserAccount(requireContext()) != "") {

            // 設定登入按鈕隱藏，因為已經登入了
            viewModel.loginButtonVisible.value = View.GONE
            viewModel.profileLinearLayoutVisible.value = View.VISIBLE

            viewModel.profileUserId.value = UserInformationManager.getUserID(requireContext())
            viewModel.profileUserName.value = UserInformationManager.getUserName(requireContext())
            viewModel.profileUserAccount.value = UserInformationManager.getUserAccount(requireContext())

            viewDataBinding.profileLogoutButton.setOnClickListener {
                TokenManager.getToken(requireContext())?.let { token ->
                    viewModel.logout(token)
                }
            }

            viewDataBinding.profileOrderButton.setOnClickListener {
                TokenManager.getToken(requireContext())?.let {
                    findNavController().navigate(R.id.action_profile_fragment_to_query_order_list_fragment)
                }
            }
        } else {

            // 設定主界面隱藏，顯示「登入會員」的按鈕
            viewModel.loginButtonVisible.value = View.VISIBLE
            viewModel.profileLinearLayoutVisible.value = View.GONE

            viewDataBinding.profileLoginButton.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }

        viewModel.viewModelInternetStatus.observe(this, EventObserver {
            when(it) {
                VIEW_MODEL_INTERNET_SUCCESS -> {
                    TokenManager.setToken(requireContext(), "")
                    UserInformationManager.saveUserID(requireContext(), 0)
                    UserInformationManager.saveUserName(requireContext(), "")

                    findNavController().navigate(R.id.action_profile_fragment_to_main_fragment)
                }
                VIEW_MODEL_INTERNET_CONNECTION_EXCEPTION ->
                    Toast.makeText(requireContext(), "網路連線異常，請確認網路狀態", Toast.LENGTH_LONG).show()
                VIEW_MODEL_INTERNET_ERROR ->
                    Toast.makeText(requireContext(), "網路錯誤，請稍等", Toast.LENGTH_LONG).show()
            }
        })
    }
}