package com.example.trabajopractico_jonatanpalavecino.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.trabajopractico_jonatanpalavecino.databinding.FragmentLoginBinding
import com.example.trabajopractico_jonatanpalavecino.viewModel.AuthViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.emailMaterialInput.addTextChangedListener { email ->
            viewModel.validateEmail(email = email.toString())
        }

        binding.passwordMaterialInput.addTextChangedListener { password ->
            viewModel.validatePassword(password = password.toString())
        }

        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                is AuthViewModel.States.SuccessEmail -> {
                    binding.emailMaterialLayout.error = null
                }
                is AuthViewModel.States.ErrorEmail -> {
                    binding.emailMaterialLayout.error = "Formato de email invalido"
                }
                is AuthViewModel.States.SuccessPassword -> {
                    binding.passwordMaterialLayout.error = null
                }
                is AuthViewModel.States.ErrorPassword -> {
                    binding.passwordMaterialLayout.error = "Minimo: ${state.password.length}/4 caracteres"
                }
                is AuthViewModel.States.SuccessBtn -> {
                    binding.btn.isEnabled = true
                }
                is AuthViewModel.States.ErrorBtn -> {
                    binding.btn.isEnabled = false
                }
            }
        })

        binding.btn.setOnClickListener {
            Toast.makeText(requireContext(), "Boton cllickeado", Toast.LENGTH_LONG).show()
        }
    }
}