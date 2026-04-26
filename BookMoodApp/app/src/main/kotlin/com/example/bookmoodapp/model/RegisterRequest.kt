package com.example.bookmoodapp.model


data class RegisterRequest(
    val nombre: String,
    val email: String,
    val password: String
)