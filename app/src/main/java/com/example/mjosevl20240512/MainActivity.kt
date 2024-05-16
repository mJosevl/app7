package com.example.mjosevl20240512

import SessionManager
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), FetchBitcoinPriceTask.OnFetchCompleteListener {

    private lateinit var welcomeTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var bitcoinPriceAdapter: BitcoinPriceAdapter
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionManager = SessionManager(this)

        welcomeTextView = findViewById(R.id.welcomeTextView)
        recyclerView = findViewById(R.id.recyclerView)
        val updateButton = findViewById<Button>(R.id.updateButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)

        welcomeTextView.text = "Bienvenido: ${sessionManager.getUsername()}"

        recyclerView.layoutManager = LinearLayoutManager(this)
        bitcoinPriceAdapter = BitcoinPriceAdapter(emptyList())
        recyclerView.adapter = bitcoinPriceAdapter

        updateButton.setOnClickListener {
            updateBitcoinPrice()
        }

        logoutButton.setOnClickListener {
            sessionManager.setLogin(false)
            sessionManager.setUsername("")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Cargar datos al iniciar la aplicación
        updateBitcoinPrice()
    }

    private fun updateBitcoinPrice() {
        FetchBitcoinPriceTask(this).execute()
    }

    override fun onFetchComplete(prices: List<BitcoinPrice>) {
        if (prices.isNotEmpty()) {
            bitcoinPriceAdapter = BitcoinPriceAdapter(prices)
            recyclerView.adapter = bitcoinPriceAdapter
        } else {
            Toast.makeText(this, "Error al obtener datos del precio del Bitcoin", Toast.LENGTH_SHORT).show()
        }
    }
}
