package com.app.employeeservice.network

import com.app.employeeservice.model.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    // =========================================================
    // LOGIN API
    // =========================================================
    //
    // POST
    // https://employee.app.com.pk/empinfo.php
    //
    // Body:
    // action=user_login
    // emp=9016
    // pass=app123
    //
    // Content-Type:
    // application/x-www-form-urlencoded
    // =========================================================

    @FormUrlEncoded
    @POST("empinfo.php")
    suspend fun login(
        @Field("action") action: String = "user_login",
        @Field("emp") employeeId: String,
        @Field("pass") password: String
    ): Response<LoginResponse>


    // =========================================================
    // GET ALL EMPLOYEE IDs
    // =========================================================
    //
    // GET
    // https://employee.app.com.pk/empdata.php?action=employees
    //
    // Example response:
    //
    // {
    //   "success": true,
    //   "data": "425,435,557,..."
    // }
    // =========================================================

    @GET("empdata.php")
    suspend fun getAllEmployees(
        @Query("action") action: String = "employees"
    ): Response<LoginResponse>


    // =========================================================
    // SALARY API
    // =========================================================
    //
    // GET
    // empdata.php?action=employees_salary
    //     &id=1804
    //     &year=2026-2027
    //
    // =========================================================

    @GET("empdata.php")
    suspend fun getEmployeeSalary(
        @Query("action") action: String = "employees_salary",
        @Query("id") employeeId: String,
        @Query("year") financialYear: String
    ): Response<Any>


    // =========================================================
    // TAX API
    // =========================================================
    //
    // GET
    // empdata.php?action=employees_tax
    //     &id=1804
    //     &year=2025-2026
    //
    // =========================================================

    @GET("empdata.php")
    suspend fun getEmployeeTax(
        @Query("action") action: String = "employees_tax",
        @Query("id") employeeId: String,
        @Query("year") financialYear: String
    ): Response<Any>
}