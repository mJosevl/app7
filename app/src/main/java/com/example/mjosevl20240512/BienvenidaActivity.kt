package com.example.mjosevl20240512
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class BienvenidaActivity : AppCompatActivity() {
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bienvenida)

        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            verificarSiUsuarioEstaLogueado()
        }
        handler.postDelayed(runnable, 3000)
    }

    private fun verificarSiUsuarioEstaLogueado() {
        val sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("loggedIn", false)
        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        finish()
    }

    override fun onDestroy() {
        handler.removeCallbacks(runnable)
        super.onDestroy()
    }
}

