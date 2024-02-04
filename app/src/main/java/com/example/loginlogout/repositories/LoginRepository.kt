package com.example.loginlogout.repositories

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.loginlogout.dataModel.User
import com.example.loginlogout.di.ApiConfig
import com.example.loginlogout.di.AuthService
import com.example.loginlogout.responses.LoginResponse
import retrofit2.Response


interface LoginRepository {
    suspend fun getResponseLogin(email: String, password: String):  Response<LoginResponse>?
}
class DefaultLoginRepository(private val authenticationService: AuthService): LoginRepository {

    var currentUser: User? by mutableStateOf(null)
    var isLoggedIn by mutableStateOf(false)
        private set
    override suspend fun getResponseLogin(email: String, password: String): Response<LoginResponse> {
        val response = authenticationService.login(email, password)
        if (response.isSuccessful) {
            val loginResponse = response.body() ?: return response
            return Response.success(loginResponse)
        } else {
            return response // Return the raw error response
        }
    }

//    companion object{
//        @Volatile
//        private var instance: LoginRepository?=null
//
//        fun getInstance()= instance?: synchronized(this){
//            instance?: LoginRepository().also { instance=it }
//        }
//    }

//    override suspend fun login(email: String, password: String): LoginResponse? {
//        TODO("Not yet implemented")
//    }

}