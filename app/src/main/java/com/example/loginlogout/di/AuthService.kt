package com.example.loginlogout.di

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.loginlogout.dataModel.User
import com.example.loginlogout.interfaces.IAuthService
import com.example.loginlogout.responses.ErrorResponse
import com.example.loginlogout.responses.LoginResponse
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="auth_token")
class AuthService(private val context: Context, override val apiService: ApiConfig): IAuthService {
    var currentUser: User? by mutableStateOf(null)
    var isLoggedIn by mutableStateOf(false)
        private set

    override suspend fun login(email: String, password: String): Response<LoginResponse> {

        try {
            val response = apiService.getLoginResponse(email, password)

            if (response.isSuccessful) {

                val loginResponse = response.body()
                if (loginResponse != null) {
                    storeToken(loginResponse.token)
                }
                if (loginResponse != null) {
                    currentUser = loginResponse.user
                }
                return Response.success(loginResponse)
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
        }catch (e: Exception){
            // Handle exceptions and return an error response
            val errorMessage = e.message ?: "Unknown error"
            val errorResponseBody = errorMessage.toResponseBody("text/plain".toMediaTypeOrNull())
            return Response.error(500, errorResponseBody) // You may customize the HTTP status code
        }

    }

    suspend fun logout(){
        storeToken("")
        isLoggedIn=false
    }

    private suspend fun storeToken(token: String){
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String?{
        val preferences = context.dataStore.data.first()
        return preferences[AUTH_TOKEN_KEY]
    }

    companion object {
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
    }
}