package com.app.employeeservice.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("success")
    val success: Boolean,

    @SerializedName("data")
    val data: String?
)