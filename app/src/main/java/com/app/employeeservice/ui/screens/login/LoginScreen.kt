package com.app.employeeservice.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.app.employeeservice.viewmodel.LoginViewModel


@Composable
fun LoginScreen(
    onLoginSuccess: (String) -> Unit,
    onForgotPasswordClick: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {

    // =========================================================
    // LOGIN INPUT
    // =========================================================

    var username by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }


    // =========================================================
    // VIEWMODEL STATE
    // =========================================================

    val isLoading by
    loginViewModel.isLoading.collectAsState()

    val errorMessage by
    loginViewModel.errorMessage.collectAsState()

    val loginSuccessful by
    loginViewModel.loginSuccessful.collectAsState()

    val loggedInEmployeeId by
    loginViewModel.employeeId.collectAsState()


    // =========================================================
    // LOGIN SUCCESS
    // =========================================================

    LaunchedEffect(
        loginSuccessful,
        loggedInEmployeeId
    ) {

        if (
            loginSuccessful &&
            loggedInEmployeeId.isNotEmpty()
        ) {

            onLoginSuccess(
                loggedInEmployeeId
            )

            loginViewModel.resetLoginState()
        }
    }


    // =========================================================
    // FORM VALIDATION
    // =========================================================

    val isFormValid =
        username.trim().isNotEmpty() &&
                password.isNotEmpty()


    // =========================================================
    // SCREEN
    // =========================================================

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 28.dp
            ),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center
    ) {


        // =====================================================
        // APP TITLE
        // =====================================================

        Text(
            text = "APP",

            fontSize = 40.sp,

            fontWeight =
                FontWeight.Bold
        )


        Spacer(
            modifier =
                Modifier.height(8.dp)
        )


        Text(
            text =
                "Associated Press of Pakistan",

            fontSize = 16.sp
        )


        Spacer(
            modifier =
                Modifier.height(6.dp)
        )


        Text(
            text =
                "Employee Self Service",

            fontSize = 22.sp,

            fontWeight =
                FontWeight.SemiBold
        )


        Spacer(
            modifier =
                Modifier.height(40.dp)
        )


        // =====================================================
        // EMPLOYEE ID
        // =====================================================

        OutlinedTextField(

            value = username,

            onValueChange = {

                if (
                    it.length <= 50
                ) {

                    username = it
                }
            },

            label = {
                Text(
                    "Employee ID"
                )
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true,

            enabled =
                !isLoading,

            isError =
                errorMessage.isNotEmpty(),

            shape =
                RoundedCornerShape(
                    12.dp
                )
        )


        Spacer(
            modifier =
                Modifier.height(16.dp)
        )


        // =====================================================
        // PASSWORD
        // =====================================================

        OutlinedTextField(

            value = password,

            onValueChange = {

                if (
                    it.length <= 100
                ) {

                    password = it
                }
            },

            label = {
                Text(
                    "Password"
                )
            },

            modifier =
                Modifier.fillMaxWidth(),

            singleLine = true,

            enabled =
                !isLoading,

            visualTransformation =
                PasswordVisualTransformation(),

            isError =
                errorMessage.isNotEmpty(),

            shape =
                RoundedCornerShape(
                    12.dp
                )
        )


        // =====================================================
        // ERROR MESSAGE
        // =====================================================

        if (
            errorMessage.isNotEmpty()
        ) {

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(
                text =
                    errorMessage,

                fontSize =
                    13.sp
            )
        }


        Spacer(
            modifier =
                Modifier.height(24.dp)
        )


        // =====================================================
        // LOGIN BUTTON
        // =====================================================

        Button(

            onClick = {

                loginViewModel.login(

                    employeeId =
                        username,

                    password =
                        password
                )
            },

            enabled =
                isFormValid &&
                        !isLoading,

            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(52.dp),

            shape =
                RoundedCornerShape(
                    12.dp
                )
        ) {

            if (isLoading) {

                CircularProgressIndicator(

                    modifier =
                        Modifier.height(
                            22.dp
                        ),

                    strokeWidth =
                        2.dp
                )

            } else {

                Text(

                    text =
                        "LOGIN",

                    fontSize =
                        16.sp,

                    fontWeight =
                        FontWeight.SemiBold
                )
            }
        }


        Spacer(
            modifier =
                Modifier.height(8.dp)
        )


        // =====================================================
        // FORGOT PASSWORD
        // =====================================================

        TextButton(

            onClick = {

                if (!isLoading) {

                    onForgotPasswordClick()
                }
            },

            enabled =
                !isLoading
        ) {

            Text(
                text =
                    "Forgot Password?"
            )
        }
    }
}