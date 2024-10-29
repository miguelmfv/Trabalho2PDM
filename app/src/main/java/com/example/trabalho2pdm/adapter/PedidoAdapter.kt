package com.example.trabalho2pdm.adapter

import android.content.Context
import android.provider.Settings.Global
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.example.trabalho2pdm.R
import com.example.trabalho2pdm.data.dao.DAOProduto
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.data.model.Pedido
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PedidoAdapter(context: Context,
                    private val pedidos: List<Pedido>) : ArrayAdapter<Pedido>(context, 0, pedidos)
{
    private lateinit var produtoDAO: DAOProduto

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val listItemView = convertView ?: LayoutInflater.from(context).inflate(R.layout.profile_activity_adapter, parent, false)

        val currentPedido = pedidos[position]

        val db = AppDatabase.getDatabase(context)

        produtoDAO = db.produtoDAO()
        GlobalScope.launch(Dispatchers.IO){
            val produto = produtoDAO.selectProdutoID(currentPedido.produtoID)
            val pedidosNameTextView: TextView = listItemView.findViewById(R.id.itemComprado)
            val pedidosPriceTextView: TextView = listItemView.findViewById(R.id.precoPago)
            val pedidosTitulo: TextView = listItemView.findViewById(R.id.tituloPedido)
            if (produto != null) {
                pedidosNameTextView.text = produto.nomeProduto
                pedidosPriceTextView.text = "$${produto.valor}"
                pedidosTitulo.text = "Pedido número: ${currentPedido.idPedido}"
            }
        }
        return listItemView
    }
}