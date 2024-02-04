package com.example.loginlogout.interfaces

import com.example.loginlogout.di.ApiConfig
import com.example.loginlogout.responses.LoginResponse
import retrofit2.Response

interface IAuthService {
    val apiService: ApiConfig

    suspend fun login(email: String, password: String): Response<LoginResponse>
}