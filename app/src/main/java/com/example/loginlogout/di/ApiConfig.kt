package com.example.loginlogout.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="auth_token")

data class TokenResponse(val token: String)



class AuthenticationService(private val context: Context)
{
    private lateinit var api:ApiService

    init {
        val client = OkHttpClient().Builder()
    }

    private inner class AuthInterceptor:Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()
            val token = getToken()

            val authenticationRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()

            return chain.proceed(authenticationRequest)
        }

    }

     suspend fun getToken():String?{
        val preferences = context.dataStore.data.first()
        return preferences[AUTH_TOKEN_KEY]
    }


    companion object{
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
    }

}