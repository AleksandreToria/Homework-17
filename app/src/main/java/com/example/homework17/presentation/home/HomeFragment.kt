package com.example.homework17.presentation.home

import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.homework17.data.datastorepreferance.DataStoreHelper
import com.example.homework17.databinding.FragmentHomeBinding
import com.example.homework17.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var navController: NavController

    override fun bindViewActionListener() {
        binding.logoutBtn.setOnClickListener {
            handleLogoutButtonClick()
        }
    }

    override fun setUp() {
        navController = findNavController()
        observeFragmentResult()
        getEmail()
    }

    private fun handleLogoutButtonClick() {
        logout()
    }

    private fun getEmail() {
        viewLifecycleOwner.lifecycleScope.launch {
            DataStoreHelper.getUserEmail(requireContext()).collect { email ->
                binding.textview.text = email
            }
        }
    }

    private fun observeFragmentResult() {
        setFragmentResultListener("LOGIN_RESULT") { _, result ->
            val email = result.getString("email", "")
            binding.textview.text = email
        }
    }

    private fun logout() {
        viewLifecycleOwner.lifecycleScope.launch {
            DataStoreHelper.clearAuthToken(requireContext())
            DataStoreHelper.clearUserEmail(requireContext())

            navController.navigate(HomeFragmentDirections.actionHomeFragmentToMainFragment())
        }
    }
}


