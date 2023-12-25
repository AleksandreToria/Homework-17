package com.example.homework17.presentation.main

import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.homework17.data.datastorepreferance.DataStoreHelper
import com.example.homework17.databinding.FragmentMainBinding
import com.example.homework17.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel by viewModels<MainFragmentViewModel>()

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
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                val authToken = context?.let { DataStoreHelper.getAuthToken(it).first() }
                if (!authToken.isNullOrBlank()) {
                    checkTokenValidityAndNavigate(authToken)
                }
            }
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


