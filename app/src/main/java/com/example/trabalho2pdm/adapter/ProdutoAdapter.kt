package com.example.trabalho2pdm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.trabalho2pdm.R
import com.example.trabalho2pdm.data.model.Produto

class ProdutoAdapter (context: Context, private var produtos: MutableList<Produto>, private val onEdit: (Produto) -> Unit, private val onDelete: (Produto) -> Unit): ArrayAdapter<Produto>(context, 0, produtos)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val produto = produtos[position]
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.main_activity_adapter, parent, false)

        val productName = view.findViewById<TextView>(R.id.ProductName)
        val productPrice = view.findViewById<TextView>(R.id.ProductPrice)

        productName.text = produto.nomeProduto
        productPrice.text = produto.valor.toString()

        return view
    }
}