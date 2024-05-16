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

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var forgotPasswordButton: Button
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)

        // Inicialización de los componentes de la interfaz de usuario
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton)

        // Comprobar si el usuario ya inició sesión anteriormente
        verificarSiUsuarioEstaLogueado()

        loginButton.setOnClickListener {
            // Ocultar el teclado virtual antes de iniciar sesión
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(loginButton.windowToken, 0)

            // Obtener el nombre de usuario y la contraseña de los EditText
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            if (username.isNotBlank() && password.isNotBlank()) {
                realizarLogin(username, password)
            } else {
                Toast.makeText(this, "Por favor, ingrese tanto el nombre de usuario como la contraseña", Toast.LENGTH_SHORT).show()
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        forgotPasswordButton.setOnClickListener {
            Toast.makeText(this, "La función de recuperar contraseña no está implementada aún", Toast.LENGTH_SHORT).show()
        }
    }

    private fun verificarSiUsuarioEstaLogueado() {
        if (sessionManager.isLoggedIn()) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun realizarLogin(username: String, password: String) {
        if (sessionManager.validateUser(username, password)) {
            sessionManager.setLogin(true)
            sessionManager.setUsername(username)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()  // Cierra LoginActivity para que el usuario no pueda volver presionando el botón atrás
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }
}
