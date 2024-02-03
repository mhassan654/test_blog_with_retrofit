package com.example.loginlogout.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.loginlogout.responses.LoginResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name="auth_token")

class ApiConfig()
{
    private lateinit var api: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.0.106:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(ApiService::class.java)
    }

    suspend fun getLoginResponse(email: String, password: String): LoginResponse {
        val res = api.login(email,password)
        print(res)
        return res
    }

    companion object{
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
    }

}