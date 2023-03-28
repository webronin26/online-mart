package com.webronin_26.online_mart.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.webronin_26.online_mart.R
import com.webronin_26.online_mart.databinding.CartSuccessFragmentBinding

class CartSuccessFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.cart_success_fragment, container, false)

        CartSuccessFragmentBinding.bind(view).apply {
            cartSuccessButton.setOnClickListener {
                findNavController().navigate(R.id.action_cart_success_fragment_to_main_fragment)
            }
        }

        return view
    }
}