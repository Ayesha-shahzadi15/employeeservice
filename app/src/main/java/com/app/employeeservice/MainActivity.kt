package com.app.employeeservice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import com.app.employeeservice.ui.screens.dashboard.DashboardScreen
import com.app.employeeservice.ui.screens.documents.DocumentsScreen
import com.app.employeeservice.ui.screens.forgot_password.ForgotPasswordScreen
import com.app.employeeservice.ui.screens.income_tax.IncomeTaxScreen
import com.app.employeeservice.ui.screens.leaves.LeavesScreen
import com.app.employeeservice.ui.screens.login.LoginScreen
import com.app.employeeservice.ui.screens.pf_loan.PFLoanScreen
import com.app.employeeservice.ui.screens.profile.ProfileScreen
import com.app.employeeservice.ui.screens.provident_fund.ProvidentFundScreen
import com.app.employeeservice.ui.screens.salary.SalaryScreen
import com.app.employeeservice.ui.screens.splash.SplashScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppContent()
        }
    }
}


@Composable
fun AppContent() {

    var currentScreen by remember {
        mutableStateOf("splash")
    }

    /*
     * Employee ID of the currently logged-in employee.
     *
     * Example:
     *
     * 9016
     */
    var loggedInEmployeeId by remember {
        mutableStateOf("")
    }


    MaterialTheme {

        when (currentScreen) {

            // =====================================================
            // SPLASH
            // =====================================================

            "splash" -> {

                SplashScreen(
                    onSplashFinished = {
                        currentScreen = "login"
                    }
                )
            }


            // =====================================================
            // LOGIN
            // =====================================================

            "login" -> {

                LoginScreen(

                    onLoginSuccess = { employeeId ->

                        loggedInEmployeeId = employeeId

                        currentScreen = "dashboard"
                    },

                    onForgotPasswordClick = {

                        currentScreen = "forgot_password"
                    }
                )
            }


            // =====================================================
            // FORGOT PASSWORD
            // =====================================================

            "forgot_password" -> {

                ForgotPasswordScreen(

                    onBackClick = {

                        currentScreen = "login"
                    }
                )
            }


            // =====================================================
            // DASHBOARD
            // =====================================================

            "dashboard" -> {

                DashboardScreen(

                    onSalaryClick = {

                        currentScreen = "salary"
                    },

                    onIncomeTaxClick = {

                        currentScreen = "income_tax"
                    },

                    onProvidentFundClick = {

                        currentScreen = "provident_fund"
                    },

                    onPFLoanClick = {

                        currentScreen = "pf_loan"
                    },

                    onLeavesClick = {

                        currentScreen = "leaves"
                    },

                    onDocumentsClick = {

                        currentScreen = "documents"
                    },

                    onProfileClick = {

                        currentScreen = "profile"
                    }
                )
            }


            // =====================================================
            // PROFILE
            // =====================================================

            "profile" -> {

                ProfileScreen(

                    onBackClick = {

                        currentScreen = "dashboard"
                    }
                )
            }


            // =====================================================
            // SALARY
            // =====================================================

            "salary" -> {

                SalaryScreen(

                    onBackClick = {

                        currentScreen = "dashboard"
                    }
                )
            }


            // =====================================================
            // INCOME TAX
            // =====================================================

            "income_tax" -> {

                IncomeTaxScreen(

                    onBackClick = {

                        currentScreen = "dashboard"
                    }
                )
            }


            // =====================================================
            // PROVIDENT FUND
            // =====================================================

            "provident_fund" -> {

                ProvidentFundScreen(

                    onBackClick = {

                        currentScreen = "dashboard"
                    }
                )
            }


            // =====================================================
            // PF LOAN
            // =====================================================

            "pf_loan" -> {

                PFLoanScreen(

                    onBackClick = {

                        currentScreen = "dashboard"
                    }
                )
            }


            // =====================================================
            // LEAVES
            // =====================================================

            "leaves" -> {

                LeavesScreen(

                    onBackClick = {

                        currentScreen = "dashboard"
                    }
                )
            }


            // =====================================================
            // DOCUMENTS
            // =====================================================

            "documents" -> {

                DocumentsScreen(

                    onBackClick = {

                        currentScreen = "dashboard"
                    }
                )
            }
        }
    }
}