package com.example.trabajopractico_jonatanpalavecino.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.trabajopractico_jonatanpalavecino.MainActivity
import com.example.trabajopractico_jonatanpalavecino.R
import com.example.trabajopractico_jonatanpalavecino.databinding.FragmentRegisterBinding
import com.example.trabajopractico_jonatanpalavecino.models.UserStorage
import com.example.trabajopractico_jonatanpalavecino.models.Users
import com.example.trabajopractico_jonatanpalavecino.viewModel.RegisterViewModel

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Si el usuario seleccionó "mantener sesion", lo deriva al home
        if (UserStorage.isLoggedIn(requireContext())) {
            goToFragment(HomeFragment(), "Inicio")
        }

        //funciones para los eventos de los inputs que valida el texto ingresado
        binding.nameMaterialInput.addTextChangedListener { name ->
            viewModel.validateName(name.toString())
        }

        binding.emailMaterialInput.addTextChangedListener { email ->
            viewModel.validateEmail(email.toString())
        }

        binding.passwordMaterialInput.addTextChangedListener { password ->
            viewModel.validatePassword(password.toString())
        }

        //funcion que setea el error dependiendo el resultado de validateName, validateEmail, validatePassword y validateBtn
        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is RegisterViewModel.States.SuccessName -> {
                    binding.nameMaterialLayout.error = null
                }
                is RegisterViewModel.States.ErrorName -> {
                    binding.nameMaterialLayout.error = "Minimo: ${state.name.length}/2 caracteres"
                }
                is RegisterViewModel.States.SuccessEmail -> {
                    binding.emailMaterialLayout.error = null
                }
                is RegisterViewModel.States.ErrorEmail -> {
                    binding.emailMaterialLayout.error = "Formato de email válido"
                }
                is RegisterViewModel.States.SuccessPassword -> {
                    binding.passwordMaterialLayout.error = null
                }
                is RegisterViewModel.States.ErrorPassword -> {
                    binding.passwordMaterialLayout.error = "Minimo ${state.password.length}/4 caracteres"
                }
                is RegisterViewModel.States.SuccessBtn -> {
                    binding.btnRegister.isEnabled = true
                }
                is RegisterViewModel.States.ErrorBtn -> {
                    binding.btnRegister.isEnabled = false
                }
            }
        })

        //Funcion que realiza una acción al clickear "registrate"
        binding.btnRegister.setOnClickListener {
            val user = Users(binding.nameMaterialInput.text.toString(), binding.emailMaterialInput.text.toString(), binding.passwordMaterialInput.text.toString())
            UserStorage.registerUser(requireContext(), user)
            if (binding.checkboxInput.isChecked) {
                UserStorage.toggleSession(requireContext(), true)
                //casteo
                (activity as? MainActivity)?.onUserLoggedIn()
            }
            Toast.makeText(requireContext(), "Usuario Registrado con éxito", Toast.LENGTH_LONG).show()
            goToFragment(HomeFragment(), "Inicio")
        }

        //Funcion que realiza una acción al clickear "Inicia sesion"
        binding.btnLogin.setOnClickListener {
            goToFragment(LoginFragment(), "Inicio de Sesión")
        }


    }

    //funcion que permite la navegacion entre fragments
    fun goToFragment(fragment: Fragment, tittleToolBar: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        //casteo para cambiar el titulo del toolbar
        (activity as? AppCompatActivity)?.supportActionBar?.title = tittleToolBar
    }
}