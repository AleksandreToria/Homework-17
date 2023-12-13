package com.example.homework17.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.homework17.BaseFragment
import com.example.homework17.common.Resource
import com.example.homework17.databinding.FragmentLoginBinding
import com.example.homework17.dataclass.Token
import com.example.homework17.sharedPreferance.SharedPreferencesHelper
import com.example.homework17.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel by viewModels<LoginViewModel>()

    override fun setUp() {
        observeFragmentResult()
        setOnClickListeners()
    }

    override fun bindViewActionListener() {
        observeLoginResult()
    }

    private fun observeFragmentResult() {
        setFragmentResultListener("REGISTER_RESULT") { _, result ->
            val email = result.getString("email", "")
            val password = result.getString("password", "")

            binding.emailEt.setText(email)
            binding.passwordEt.setText(password)
        }
    }

    private fun setOnClickListeners() {
        with(binding) {
            loginBtn.setOnClickListener {
                handleLoginButtonClick()
            }
            registerBtn.setOnClickListener {
                handleRegisterButtonClick()
            }
        }
    }

    private fun handleLoginButtonClick() {
        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()
        val rememberMe = binding.checkBox.isChecked

        if (checkUser(email, password)) {
            showMessage("Please fill all the fields")
            return
        }

        if (!isEmailValid(email)) {
            showMessage("Please enter valid email")
            return
        }

        val result = Bundle().apply {
            putString("email", binding.emailEt.text.toString())
        }

        setFragmentResult("LOGIN_RESULT", result)

        if (rememberMe) {
            SharedPreferencesHelper.saveUserEmail(requireContext(), email)
        }

        viewModel.loginUser(email, password, requireContext(), rememberMe)
    }


    private fun handleRegisterButtonClick() {
        val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
        binding.root.findNavController().navigate(action)
    }

    private fun observeLoginResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataFlow.collect { resource ->
                    resource?.let {
                        handleLoginResponse(it)
                    }
                }
            }
        }
    }

    private fun handleLoginResponse(resource: Resource<Token>) {
        when (resource) {
            is Resource.Success -> showMessage("Successful").also {
                val action = LoginFragmentDirections.actionLoginFragmentToHomeFragment()

                binding.root.findNavController().navigate(action)
            }

            is Resource.Error -> showMessage("${resource.errorMessage}")
        }
    }

    private fun checkUser(email: String, password: String): Boolean {
        return email.isEmpty() || password.isEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        val trimmedMail = email.trim()
        return trimmedMail.contains("@") && !trimmedMail.startsWith("@") && !trimmedMail.endsWith("@")
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}