package com.example.loginlogout.dataModel

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val image: String?, // Make nullable for potential absence
    val emailVerifiedAt: String?, // Make nullable for potential absence
    val createdAt: String,
    val updatedAt: String
)
