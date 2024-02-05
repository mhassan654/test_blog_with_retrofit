package com.example.loginlogout.screens.auth

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginlogout.dataModel.User
import com.example.loginlogout.repositories.LoginRepository
import com.example.loginlogout.responses.ErrorResponse
import com.example.loginlogout.responses.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class LoginViewModel (
    private  val repository: LoginRepository = LoginRepository.getInstance()): ViewModel(){

        init {
            viewModelScope.launch(Dispatchers.IO) {
            }
        }

//    val loginState: MutableState<LoginResponse> = mutableStateOf(LoginResponse())

    private suspend fun getLoginResponse(email:String, password:String):Response<LoginResponse> {
        return repository.getResponseLogin(email,password)
    }

    suspend fun login(email: String, password: String): Response<LoginResponse> {
         try {
            val response = getLoginResponse(email, password)
            if (response.isSuccessful) {
                val loginResponse = response.body()
//                if (loginResponse != null) {
//                    storeToken(loginResponse.token)
//                Log.d("Success response", loginResponse?.user.toString())
                   return Response.success(loginResponse)
//                }
            } else {
                // Handle and return appropriate error responses based on error code
                val errorBody = try {
                    response.errorBody()?.string()?.let { Gson().fromJson(it, ErrorResponse::class.java) }
                } catch (e: Exception) {
                    null
                }
                // Handle the error body and return a proper error response
                // For example:
                val errorMessage = errorBody?.errors.toString()
                val errorResponseBody = errorMessage.toResponseBody("text/plain".toMediaTypeOrNull())
//                print(errorResponseBody)
//                Log.d("Error response", errorMessage)
                return Response.error(response.code(), errorResponseBody)
            }

        } catch (e: Exception) {
            // Handle exceptions and return an error response
            val errorMessage = e.message ?: "Unknown error"
            val errorResponseBody = errorMessage.toResponseBody("text/plain".toMediaTypeOrNull())
            return Response.error(500, errorResponseBody) // You may customize the HTTP status code
        }
    }
}
