package com.app.employeeservice.repository

import com.app.employeeservice.model.LoginResponse
import com.app.employeeservice.network.RetrofitClient

class AuthRepository {

    private val api =
        RetrofitClient.apiService


    // =========================================================
    // LOGIN
    // =========================================================

    suspend fun login(
        employeeId: String,
        password: String
    ): Result<LoginResponse> {

        return try {

            val response =
                api.login(
                    employeeId = employeeId,
                    password = password
                )


            if (response.isSuccessful) {

                val body =
                    response.body()

                if (body != null) {

                    Result.success(body)

                } else {

                    Result.failure(
                        Exception(
                            "Empty response from server."
                        )
                    )
                }

            } else {

                Result.failure(
                    Exception(
                        "Server error: ${response.code()}"
                    )
                )
            }

        } catch (e: Exception) {

            Result.failure(e)
        }
    }
}