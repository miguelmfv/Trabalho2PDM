package com.example.trabalho2pdm.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.trabalho2pdm.DetailedProductActivity
import com.example.trabalho2pdm.R
import com.example.trabalho2pdm.data.model.Produto
import com.example.trabalho2pdm.databinding.ActivityMainBinding

class ProductAdapter(context: Context,
    private val produtos: List<Produto>) : ArrayAdapter<Produto>(context, 0, produtos)
{

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Verifica se a view reutilizável é nula
        val listItemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.main_activity_adapter, parent, false)
        // Obtém o produto atual
        val currentProduct = produtos[position]

        // Define os valores nos TextViews
        val productNameTextView: TextView = listItemView.findViewById(R.id.ProductName)
        productNameTextView.text = currentProduct.nomeProduto

        val productPriceTextView: TextView = listItemView.findViewById(R.id.ProductPrice)
        productPriceTextView.text = "$${currentProduct.valor}"


        return listItemView
    }
}