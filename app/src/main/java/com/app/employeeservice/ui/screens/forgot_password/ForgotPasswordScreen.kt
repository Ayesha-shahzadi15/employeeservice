package com.app.employeeservice.ui.screens.forgot_password

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val APPGreen = Color(0xFF075B32)

@Composable
fun ForgotPasswordScreen(
    onBackClick: () -> Unit
) {

    // =========================================================
    // STATE
    // =========================================================

    var email by remember {
        mutableStateOf("")
    }

    var otp by remember {
        mutableStateOf("")
    }

    var newPassword by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    var otpSent by remember {
        mutableStateOf(false)
    }

    var passwordReset by remember {
        mutableStateOf(false)
    }

    var errorMessage by remember {
        mutableStateOf("")
    }


    // =========================================================
    // MAIN SCREEN
    // =========================================================

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8FAF9))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // =====================================================
        // BACK BUTTON
        // =====================================================

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            IconButton(
                onClick = onBackClick
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = APPGreen
                )
            }
        }


        Spacer(
            modifier = Modifier.height(25.dp)
        )


        // =====================================================
        // ICON
        // =====================================================

        Icon(
            imageVector = if (passwordReset) {
                Icons.Default.Verified
            } else {
                Icons.Default.Lock
            },
            contentDescription = null,
            tint = APPGreen
        )


        Spacer(
            modifier = Modifier.height(15.dp)
        )


        // =====================================================
        // TITLE
        // =====================================================

        Text(
            text = if (passwordReset) {
                "Password Reset"
            } else {
                "Forgot Password?"
            },
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )


        Spacer(
            modifier = Modifier.height(8.dp)
        )


        // =====================================================
        // DESCRIPTION
        // =====================================================

        Text(
            text = when {

                passwordReset ->
                    "Your password has been changed successfully."

                !otpSent ->
                    "Enter your registered email to receive an OTP."

                else ->
                    "Enter the OTP sent to your registered email."
            },
            fontSize = 14.sp,
            color = Color.Gray
        )


        Spacer(
            modifier = Modifier.height(25.dp)
        )


        // =====================================================
        // SUCCESS SCREEN
        // =====================================================

        if (passwordReset) {

            Text(
                text = "Your password has been reset successfully.",
                color = APPGreen,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = APPGreen
                )
            ) {

                Text(
                    text = "Back to Login",
                    fontWeight = FontWeight.Bold
                )
            }

        } else {

            // =================================================
            // EMAIL
            // =================================================

            OutlinedTextField(
                value = email,

                onValueChange = {
                    email = it
                    errorMessage = ""
                },

                modifier = Modifier.fillMaxWidth(),

                label = {
                    Text("Registered Email")
                },

                leadingIcon = {

                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        tint = APPGreen
                    )
                },

                singleLine = true,

                shape = RoundedCornerShape(12.dp),

                enabled = !otpSent
            )


            Spacer(
                modifier = Modifier.height(15.dp)
            )


            // =================================================
            // SEND OTP
            // =================================================

            if (!otpSent) {

                Button(
                    onClick = {

                        if (email.trim().isEmpty()) {

                            errorMessage =
                                "Please enter your registered email."

                        } else if (!email.contains("@")) {

                            errorMessage =
                                "Please enter a valid email address."

                        } else {

                            errorMessage = ""

                            /*
                             * API WILL BE CONNECTED HERE
                             *
                             * Example:
                             *
                             * sendOtp(email)
                             */

                            otpSent = true
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    shape = RoundedCornerShape(12.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = APPGreen
                    )
                ) {

                    Text(
                        text = "Send OTP",
                        fontWeight = FontWeight.Bold
                    )
                }
            }


            // =================================================
            // OTP + NEW PASSWORD
            // =================================================

            if (otpSent) {

                Spacer(
                    modifier = Modifier.height(5.dp)
                )


                // -------------------------------------------------
                // OTP
                // -------------------------------------------------

                OutlinedTextField(
                    value = otp,

                    onValueChange = {

                        if (it.length <= 6) {
                            otp = it
                            errorMessage = ""
                        }
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Enter OTP")
                    },

                    leadingIcon = {

                        Icon(
                            imageVector = Icons.Default.Verified,
                            contentDescription = "OTP",
                            tint = APPGreen
                        )
                    },

                    singleLine = true,

                    shape = RoundedCornerShape(12.dp)
                )


                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // -------------------------------------------------
                // NEW PASSWORD
                // -------------------------------------------------

                OutlinedTextField(
                    value = newPassword,

                    onValueChange = {
                        newPassword = it
                        errorMessage = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("New Password")
                    },

                    leadingIcon = {

                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "New Password",
                            tint = APPGreen
                        )
                    },

                    visualTransformation =
                        PasswordVisualTransformation(),

                    singleLine = true,

                    shape = RoundedCornerShape(12.dp)
                )


                Spacer(
                    modifier = Modifier.height(12.dp)
                )


                // -------------------------------------------------
                // CONFIRM PASSWORD
                // -------------------------------------------------

                OutlinedTextField(
                    value = confirmPassword,

                    onValueChange = {
                        confirmPassword = it
                        errorMessage = ""
                    },

                    modifier = Modifier.fillMaxWidth(),

                    label = {
                        Text("Confirm New Password")
                    },

                    leadingIcon = {

                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Confirm Password",
                            tint = APPGreen
                        )
                    },

                    visualTransformation =
                        PasswordVisualTransformation(),

                    singleLine = true,

                    shape = RoundedCornerShape(12.dp)
                )


                // =================================================
                // ERROR MESSAGE
                // =================================================

                if (errorMessage.isNotEmpty()) {

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 13.sp
                    )
                }


                Spacer(
                    modifier = Modifier.height(18.dp)
                )


                // =================================================
                // RESET PASSWORD BUTTON
                // =================================================

                Button(
                    onClick = {

                        when {

                            otp.trim().isEmpty() -> {

                                errorMessage =
                                    "Please enter the OTP."
                            }

                            otp.length < 6 -> {

                                errorMessage =
                                    "OTP must contain 6 digits."
                            }

                            newPassword.isEmpty() -> {

                                errorMessage =
                                    "Please enter a new password."
                            }

                            newPassword.length < 6 -> {

                                errorMessage =
                                    "Password must contain at least 6 characters."
                            }

                            confirmPassword.isEmpty() -> {

                                errorMessage =
                                    "Please confirm your password."
                            }

                            newPassword != confirmPassword -> {

                                errorMessage =
                                    "Passwords do not match."
                            }

                            else -> {

                                errorMessage = ""

                                /*
                                 * API WILL BE CONNECTED HERE
                                 *
                                 * Example:
                                 *
                                 * verifyOtpAndResetPassword(
                                 *     email = email,
                                 *     otp = otp,
                                 *     newPassword = newPassword
                                 * )
                                 */

                                passwordReset = true
                            }
                        }
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    shape = RoundedCornerShape(12.dp),

                    colors = ButtonDefaults.buttonColors(
                        containerColor = APPGreen
                    )
                ) {

                    Text(
                        text = "Reset Password",
                        fontWeight = FontWeight.Bold
                    )
                }


                Spacer(
                    modifier = Modifier.height(8.dp)
                )


                // =================================================
                // CHANGE EMAIL
                // =================================================

                TextButton(
                    onClick = {

                        otpSent = false
                        otp = ""
                        newPassword = ""
                        confirmPassword = ""
                        errorMessage = ""
                    }
                ) {

                    Text(
                        text = "Change Email",
                        color = APPGreen
                    )
                }
            }
        }
    }
}