package com.example.homework17.fragment

import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.example.homework17.BaseFragment
import com.example.homework17.databinding.FragmentHomeBinding
import com.example.homework17.sharedPreferance.SharedPreferencesHelper

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun bindViewActionListener() {
        binding.logoutBtn.setOnClickListener {
            handleLogoutButtonClick()
        }
    }

    override fun setUp() {
        observeFragmentResult()
        getEmail()
    }

    private fun handleLogoutButtonClick() {
        logout()
        val action = HomeFragmentDirections.actionHomeFragmentToMainFragment()
        binding.root.findNavController().navigate(action)
    }

    private fun getEmail() {
        val email = SharedPreferencesHelper.getUserEmail(requireContext())
        binding.textview.text = email
    }

    private fun observeFragmentResult() {
        setFragmentResultListener("LOGIN_RESULT") { _, result ->
            val email = result.getString("email", "")

            binding.textview.text = email
        }
    }

    private fun logout() {
        SharedPreferencesHelper.clearAuthToken(requireContext())
        SharedPreferencesHelper.clearUserEmail(requireContext())
    }

}