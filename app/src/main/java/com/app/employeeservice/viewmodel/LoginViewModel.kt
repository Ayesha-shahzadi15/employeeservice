package com.app.employeeservice.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.employeeservice.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository =
        AuthRepository()


    // =========================================================
    // LOADING
    // =========================================================

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> =
        _isLoading.asStateFlow()


    // =========================================================
    // ERROR
    // =========================================================

    private val _errorMessage =
        MutableStateFlow("")

    val errorMessage: StateFlow<String> =
        _errorMessage.asStateFlow()


    // =========================================================
    // LOGIN SUCCESS
    // =========================================================

    private val _loginSuccessful =
        MutableStateFlow(false)

    val loginSuccessful: StateFlow<Boolean> =
        _loginSuccessful.asStateFlow()


    // =========================================================
    // EMPLOYEE ID
    // =========================================================

    private val _employeeId =
        MutableStateFlow("")

    val employeeId: StateFlow<String> =
        _employeeId.asStateFlow()


    // =========================================================
    // LOGIN
    // =========================================================

    fun login(
        employeeId: String,
        password: String
    ) {

        if (employeeId.trim().isEmpty()) {

            _errorMessage.value =
                "Please enter your employee ID."

            return
        }


        if (password.isEmpty()) {

            _errorMessage.value =
                "Please enter your password."

            return
        }


        viewModelScope.launch {

            _isLoading.value = true

            _errorMessage.value = ""

            _loginSuccessful.value = false


            val result =
                repository.login(
                    employeeId =
                        employeeId.trim(),

                    password =
                        password
                )


            result
                .onSuccess { response ->

                    if (response.success) {

                        _employeeId.value =
                            employeeId.trim()

                        _loginSuccessful.value =
                            true

                    } else {

                        _errorMessage.value =
                            response.data
                                ?: "Invalid employee ID or password."

                        _loginSuccessful.value =
                            false
                    }
                }

                .onFailure { error ->

                    _errorMessage.value =
                        error.message
                            ?: "Unable to connect to the server."

                    _loginSuccessful.value =
                        false
                }


            _isLoading.value = false
        }
    }


    // =========================================================
    // RESET SUCCESS
    // =========================================================

    fun resetLoginState() {

        _loginSuccessful.value =
            false

        _errorMessage.value =
            ""
    }
}