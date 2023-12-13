package com.example.homework17.fragment

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.homework17.BaseFragment
import com.example.homework17.common.Resource
import com.example.homework17.databinding.FragmentRegisterBinding
import com.example.homework17.dataclass.Token
import com.example.homework17.viewmodel.RegisterViewModel
import kotlinx.coroutines.launch


class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel by viewModels<RegisterViewModel>()

    override fun bindViewActionListener() {
        observeRegisterResult()
    }

    override fun setUp() {
        with(binding) {
            registerBtn.setOnClickListener {
                handleRegisterButtonClick()
            }
        }
    }

    private fun observeRegisterResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataFlow.collect { resource ->
                    resource?.let {
                        handleRegisterResponse(it)
                    }
                }
            }
        }
    }

    private fun handleRegisterResponse(resource: Resource<Token>) {
        when (resource) {
            is Resource.Success -> showMessage("Successful").also {
                val email = binding.emailEt.text.toString()
                val password = binding.passwordEt.text.toString()

                val result = Bundle().apply {
                    putString("email", email)
                    putString("password", password)
                }

                setFragmentResult("REGISTER_RESULT", result)

                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                binding.root.findNavController().navigate(action)
            }

            is Resource.Error -> showMessage("${resource.errorMessage}")
        }
    }

    private fun handleRegisterButtonClick() {
        val email = binding.emailEt.text.toString()
        val password = binding.passwordEt.text.toString()
        val repeatPassword = binding.repeatPasswordEt.text.toString()

        if (checkUser(email, password, repeatPassword)) {
            showMessage("Please fill all the fields")
            return
        }

        if (!passwordMatcher(password, repeatPassword)) {
            showMessage("Passwords do not match")
            return
        }

        if (!isEmailValid(email)) {
            showMessage("Please enter valid email")
            return
        }

        viewModel.registerUser(email, password)
    }

    private fun passwordMatcher(password: String, repeatPassWord: String): Boolean {
        return password == repeatPassWord
    }

    private fun checkUser(email: String, password: String, repeatPassWord: String): Boolean {
        return email.isEmpty() || password.isEmpty() || repeatPassWord.isEmpty()
    }

    private fun isEmailValid(email: String): Boolean {
        val trimmedMail = email.trim()
        return trimmedMail.contains("@") && !trimmedMail.startsWith("@") && !trimmedMail.endsWith("@")
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}