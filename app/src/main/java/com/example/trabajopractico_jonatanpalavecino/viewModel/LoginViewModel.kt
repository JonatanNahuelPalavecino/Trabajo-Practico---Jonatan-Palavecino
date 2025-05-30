package com.example.trabajopractico_jonatanpalavecino.viewModel

import androidx.core.util.PatternsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    private var email: String = ""
    private var password: String = ""
    var viewState = MutableLiveData<States>()

    fun validateEmail(email: String) {
        this.email = email
        if (email.isNotBlank() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()) {
            viewState.value = States.SuccessEmail
        } else {
            viewState.value = States.ErrorEmail
        }
        validateBtn()
    }

    fun validatePassword(password: String) {
        this.password = password
        if (password.isNotBlank() && password.length > 3) {
            viewState.value = States.SuccessPassword
        } else {
            viewState.value = States.ErrorPassword(password = password)
        }
        validateBtn()
    }

    private fun validateBtn() {
        if (email.isNotBlank() && PatternsCompat.EMAIL_ADDRESS.matcher(email).matches() && password.isNotBlank() && password.length > 3) {
            viewState.value = States.SuccessBtn
        } else {
            viewState.value = States.ErrorBtn
        }
    }


    sealed class States() {
        object SuccessEmail: States()
        object  ErrorEmail: States()
        object SuccessPassword: States()
        data class ErrorPassword(val password: String): States()
        object SuccessBtn : States()
        object ErrorBtn: States()
    }
}