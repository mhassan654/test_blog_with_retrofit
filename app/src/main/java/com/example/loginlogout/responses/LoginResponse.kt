package com.example.loginlogout.responses

import com.example.loginlogout.dataModel.User
import com.google.gson.annotations.SerializedName


data class LoginResponse(
    val user: User,
    val token: String
)


data class ErrorResponse(
    val message: String,
    val errors: Map<String, List<String>>
)
