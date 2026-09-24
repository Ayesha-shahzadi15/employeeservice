package com.app.employeeservice.model

data class LoginRequest(
    val employeeId: String,
    val password: String
)