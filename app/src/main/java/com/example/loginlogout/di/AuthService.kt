package com.example.loginlogout.di

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.loginlogout.dataModel.User

object AuthService {
    var currentUser: User? by mutableStateOf(null)

    suspend fun login(email: String, password: String): Boolean {
        // Simulate API call and return true if successful
        return true
    }

    suspend fun logout() {
        currentUser = null
    }
}
