package com.example.loginlogout.screens.auth

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginlogout.di.AuthService
import com.example.loginlogout.repositories.DefaultLoginRepository
import com.example.loginlogout.repositories.LoginRepository
import com.example.loginlogout.responses.ErrorResponse
import com.example.loginlogout.responses.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel (
    private  val loginRepository: DefaultLoginRepository
): ViewModel(){

        init {
            viewModelScope.launch(Dispatchers.IO) {
            }
        }

    private suspend fun getLoginResponse(email:String, password:String): retrofit2.Response<LoginResponse> {
        return loginRepository.getResponseLogin(email,password)
    }

    suspend fun login(email: String, password: String): Any {
        return try {

            val response = getLoginResponse(email, password)

            if (response.isSuccessful) {
                val loginResponse = response.body() ?: return false
Log.d("Loading user data", loginResponse.toString())
                print(loginResponse.user)
                print(loginResponse.token)
//                storeToken(loginResponse.token)
//                AuthService.currentUser = loginResponse.user
//                isLoggedIn = true
                return Response.User(loginResponse)

            } else {
                val errorResponse = try {
                    response.errorBody()?.string()?.let { Gson().fromJson(it, ErrorResponse::class.java) }
                } catch (e: Exception) {
                    null
                }
                Log.d("falied", errorResponse.toString())
                // Handle error based on errorResponse (e.g., display message)
                return false
            }
        } catch (e: Exception) {
            Response.Error(e.message ?: "Login failed")
        }
    }

}

sealed class Response {
    data class User(val user: LoginResponse? = null): Response()
    data class Success(val data: Any? = null) : Response()
    data class Error(val message: String?) : Response()
}