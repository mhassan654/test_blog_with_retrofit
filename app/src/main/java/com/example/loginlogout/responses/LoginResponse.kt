package com.example.loginlogout.responses

import com.example.loginlogout.dataModel.User
import com.google.gson.annotations.SerializedName


data class LoginResponse(
    val user: User,
    val token: String
) {

   public final data class User(
        val id: Int,
        val name: String,
        val email: String,
        val image: String?, // Make nullable for potential absence
        val emailVerifiedAt: String?, // Make nullable for potential absence
        val createdAt: String,
        val updatedAt: String
    )

}


data class ErrorResponse(
    val message: String,
    val errors: Map<String, List<String>>
)
