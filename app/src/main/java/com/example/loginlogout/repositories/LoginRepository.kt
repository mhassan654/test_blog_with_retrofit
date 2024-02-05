package com.example.loginlogout.repositories

import com.example.loginlogout.di.ApiConfig
import com.example.loginlogout.responses.LoginResponse
import retrofit2.Response

class LoginRepository(private val apiService: ApiConfig = ApiConfig()) {
    suspend fun getResponseLogin(email: String, pass: String): Response<LoginResponse> {
        return apiService.getLoginResponse(email, pass)
    }

    companion object{
        @Volatile
        private var instance: LoginRepository?=null

        fun getInstance()= instance?: synchronized(this){
            instance?: LoginRepository().also { instance=it }
        }
    }

}