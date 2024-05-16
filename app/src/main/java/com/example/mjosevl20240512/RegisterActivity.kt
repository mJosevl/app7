package com.example.mjosevl20240512

import SessionManager


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mjosevl20240512.data.Usuario

class RegisterActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var fullNameEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        sessionManager = SessionManager(this)

        // Inicialización de los componentes de la interfaz de usuario
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        fullNameEditText = findViewById(R.id.fullName)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            // Ocultar el teclado virtual antes de registrar
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(registerButton.windowToken, 0)

            // Obtener los datos de los EditText
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val fullName = fullNameEditText.text.toString().trim()

            if (username.isNotBlank() && password.isNotBlank() && fullName.isNotBlank()) {
                val newUser = Usuario(username, password, fullName)
                if (sessionManager.registerUser(newUser)) {
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    finish()  // Regresa a LoginActivity después del registro
                } else {
                    Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
