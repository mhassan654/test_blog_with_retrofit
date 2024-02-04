package com.example.loginlogout.screens.auth

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginlogout.repositories.LoginRepository
import com.example.loginlogout.responses.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel (
    private  val repository: LoginRepository = LoginRepository.getInstance()): ViewModel(){

        init {
            viewModelScope.launch(Dispatchers.IO) {
            }
        }

//    val loginState: MutableState<List<LoginResponse>> = mutableStateOf(emptyList<LoginResponse>())

    private suspend fun getLoginResponse(email:String, password:String):LoginResponse {
        return repository.getResponseLogin(email,password)
    }

    suspend fun login(email: String, password: String): Response {
        return try {
            val response = getLoginResponse(email, password)
            println(response.token)
            Response.User(response.user)
        } catch (e: Exception) {
            Response.Error(e.message ?: "Login failed")
        }
    }
}

sealed class Response {
    data class User(val user: LoginResponse.User? = null): Response()
    data class Success(val data: Any? = null) : Response()
    data class Error(val message: String?) : Response()
}