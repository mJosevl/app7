package com.example.mjosevl20240512


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class BitcoinPrice(val fecha: String, val valor: Double)

class BitcoinPriceAdapter(private val prices: List<BitcoinPrice>) : RecyclerView.Adapter<BitcoinPriceAdapter.BitcoinPriceViewHolder>() {

    class BitcoinPriceViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.dateTextView)
        val priceTextView: TextView = view.findViewById(R.id.priceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BitcoinPriceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bitcoin_price, parent, false)
        return BitcoinPriceViewHolder(view)
    }

    override fun onBindViewHolder(holder: BitcoinPriceViewHolder, position: Int) {
        val price = prices[position]
        holder.dateTextView.text = price.fecha
        holder.priceTextView.text = price.valor.toString()
    }

    override fun getItemCount() = prices.size
}
