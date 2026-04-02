package com.example.bookmoodapp.model

data class UsuarioLibroRequest(
    val idUsuario: Long,
    val idLibro: Long,
    val favorito: Boolean,
    val leido: Boolean,
    val quieroLeer: Boolean
)
