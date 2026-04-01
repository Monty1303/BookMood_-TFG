package com.example.bookmoodapp.model

data class Libro (
    val idLibro: Long,
    val titulo: String,
    val autor: String?,
    val sinopsis: String?,
    val portadaUrl: String?,
    val genero: String?,
)
