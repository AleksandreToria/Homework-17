package com.example.homework17.presentation.home

import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.homework17.databinding.FragmentHomeBinding
import com.example.homework17.presentation.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    private lateinit var navController: NavController
    private val viewModel by viewModels<HomeFragmentViewModel>()

    override fun bindViewActionListener() {
        binding.logoutBtn.setOnClickListener {
            logout()
        }
    }

    override fun setUp() {
        navController = findNavController()
        getEmail()
        observeFragmentResult()
    }

    private fun getEmail() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataFlow.collect {
                    if (!it.isNullOrBlank()) {
                        binding.textview.text = it
                    }
                }
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
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.clearUserEmail()
                viewModel.clearAuthToken()

                binding.root.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToMainFragment())
            }
        }
    }
}


