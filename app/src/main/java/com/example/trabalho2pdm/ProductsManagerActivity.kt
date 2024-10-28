package com.example.trabalho2pdm

import android.R
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho2pdm.data.dao.DAOProduto
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.data.model.Produto
import com.example.trabalho2pdm.databinding.ProductsManagerActivityBinding

class ProductsManagerActivity: AppCompatActivity() {
    private lateinit var binding: ProductsManagerActivityBinding
    private lateinit var produtoDAO: DAOProduto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProductsManagerActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val userId = sharedPref.getInt("user_id", -1)

        val db = AppDatabase.getDatabase(this)
        produtoDAO = db.produtoDAO()

        val actions = listOf("Selecione uma ação", "Editar Produto", "Deletar Produto")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, actions)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.spinnerProductActions.adapter = adapter

        binding.buttonConfirmarAcao.setOnClickListener {
            val acaoSelecionada = binding.spinnerProductActions.selectedItem.toString()
            when (acaoSelecionada) {
                "Editar Produto" -> mostrarEditar()

                "Deletar Produto" -> Log.d("ProdutoActivity", "Ação: Deletar Produto")
                else -> Log.d("ProdutoActivity", "Nenhuma ação válida selecionada")
            }
        }
    }

    private fun mostrarEditar() {

    }
}