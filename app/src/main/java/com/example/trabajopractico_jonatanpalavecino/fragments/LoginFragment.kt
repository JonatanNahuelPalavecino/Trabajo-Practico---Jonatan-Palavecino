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
import com.example.trabajopractico_jonatanpalavecino.databinding.FragmentLoginBinding
import com.example.trabajopractico_jonatanpalavecino.models.UserStorage
import com.example.trabajopractico_jonatanpalavecino.viewModel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Si el usuario seleccionó "mantener sesion", lo deriva al home
        if (UserStorage.isLoggedIn(requireContext())) {
            goToFragment(HomeFragment(), "Inicio")
        }

        //funciones para los eventos de los inputs que valida el texto ingresado
        binding.emailMaterialInput.addTextChangedListener { email ->
            viewModel.validateEmail(email = email.toString())
        }

        binding.passwordMaterialInput.addTextChangedListener { password ->
            viewModel.validatePassword(password = password.toString())
        }

        //funcion que setea el error dependiendo el resultado de validateEmail, validatePassword y validateBtn
        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            when(state) {
                is LoginViewModel.States.SuccessEmail -> {
                    binding.emailMaterialLayout.error = null
                }
                is LoginViewModel.States.ErrorEmail -> {
                    binding.emailMaterialLayout.error = "Formato de email invalido"
                }
                is LoginViewModel.States.SuccessPassword -> {
                    binding.passwordMaterialLayout.error = null
                }
                is LoginViewModel.States.ErrorPassword -> {
                    binding.passwordMaterialLayout.error = "Minimo: ${state.password.length}/4 caracteres"
                }
                is LoginViewModel.States.SuccessBtn -> {
                    binding.btnLogin.isEnabled = true
                }
                is LoginViewModel.States.ErrorBtn -> {
                    binding.btnLogin.isEnabled = false
                }
            }
        })

        //Funcion que realiza una acción al clickear "iniciar sesión"
        binding.btnLogin.setOnClickListener {

            val userExist = UserStorage.loginUser(requireContext(), binding.emailMaterialInput.text.toString(), binding.passwordMaterialInput.text.toString())
            if (userExist) {
                Toast.makeText(requireContext(), "Inicio de Sesión Correcto", Toast.LENGTH_LONG).show()

                if (binding.checkboxInput.isChecked) {
                    UserStorage.toggleSession(requireContext(), true)
                }

                //casteo del activity para ejecutar una funcion
                (activity as? MainActivity)?.onUserLoggedIn()
                goToFragment(HomeFragment(), "Inicio")
            } else {
                Toast.makeText(requireContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show()
            }
        }

        //Funcion que realiza una acción al clickear "Registrate"
        binding.btnRegister.setOnClickListener {
            goToFragment(RegisterFragment(), "Registro de Usuario")
        }

    }

    //funcion que permite la navegacion entre fragments
    fun goToFragment(fragment: Fragment, titleToolbar: String) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()

        //casteo del activity para aplicar el titulo en el toolbar
        (activity as? AppCompatActivity)?.supportActionBar?.title = titleToolbar
    }


}