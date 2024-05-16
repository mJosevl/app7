package com.example.mjosevl20240512.data

object GestorUsuarios {
    private val usuarios = mutableListOf<Usuario>()

    fun agregarUsuario(usuario: Usuario) {
        usuarios.add(usuario)
    }

    fun usuarioValido(username: String, password: String): Boolean {
        return usuarios.any { it.username == username && it.password == password }
    }
}
