package com.example.homework17.fragment

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.homework17.BaseFragment
import com.example.homework17.databinding.FragmentMainBinding
import com.example.homework17.sharedPreferance.SharedPreferencesHelper
import com.example.homework17.viewmodel.MainViewModel
import kotlinx.coroutines.launch

class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel by viewModels<MainViewModel>()

    override fun bindViewActionListener() {
        listeners()
    }

    override fun setUp() {
        sessionCheck()
        listeners()
    }

    private fun listeners() {
        with(binding) {
            registerBtn.setOnClickListener {
                val action = MainFragmentDirections.actionHomeFragmentToRegisterFragment()
                binding.root.findNavController().navigate(action)
            }

            loginBtn.setOnClickListener {
                val action = MainFragmentDirections.actionHomeFragmentToLoginFragment()
                binding.root.findNavController().navigate(action)
            }
        }
    }

    private fun sessionCheck() {
        val authToken = SharedPreferencesHelper.getAuthToken(requireContext())
        if (!authToken.isNullOrBlank()) {
            checkTokenValidityAndNavigate(authToken)
        }
    }

    private fun checkTokenValidityAndNavigate(token: String) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val isValidToken = viewModel.checkTokenValidity(token)
                if (isValidToken) {
                    navigateToHomeFragment()
                }
            }
        }
    }

    private fun navigateToHomeFragment() {
        val action = MainFragmentDirections.actionMainFragmentToHomeFragment()
        binding.root.findNavController().navigate(action)
    }
}


