package com.example.mjosevl20240512

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import org.json.JSONObject

class FetchBitcoinPriceTask(private val listener: OnFetchCompleteListener) : AsyncTask<Void, Void, List<BitcoinPrice>>() {

    interface OnFetchCompleteListener {
        fun onFetchComplete(prices: List<BitcoinPrice>)
    }

    override fun doInBackground(vararg params: Void?): List<BitcoinPrice>? {
        val apiUrl = "https://mindicador.cl/api/bitcoin"
        val prices = mutableListOf<BitcoinPrice>()

        try {
            val url = URL(apiUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                bufferedReader.useLines { lines -> lines.forEach { response.append(it) } }

                val jsonResponse = JSONObject(response.toString())
                val serieArray = jsonResponse.getJSONArray("serie")
                for (i in 0 until serieArray.length()) {
                    val entry = serieArray.getJSONObject(i)
                    val fecha = entry.getString("fecha")
                    val valor = entry.getDouble("valor")
                    prices.add(BitcoinPrice(fecha, valor))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return prices
    }

    override fun onPostExecute(result: List<BitcoinPrice>?) {
        super.onPostExecute(result)
        if (result != null) {
            listener.onFetchComplete(result)
        } else {
            listener.onFetchComplete(emptyList())
        }
    }
}

