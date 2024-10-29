package com.example.trabalho2pdm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho2pdm.data.dao.DAOProduto
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.data.model.Produto
import com.example.trabalho2pdm.databinding.ProductsManagerActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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

        binding.btnAddProduct.setOnClickListener {
            val nome = binding.fieldProductName.text.toString()
            val desc = binding.fieldProductDescription.text.toString()
            val valor = binding.fieldProductPrice.text.toString()

            if (nome.isNotEmpty() && desc.isNotEmpty() && valor.isNotEmpty()){
                val valorzao = valor.toDouble()
                val produto = Produto(nomeProduto = nome, descricao = desc, valor = valorzao)
                GlobalScope.launch(Dispatchers.IO) {
                    produtoDAO.insertProduto(produto)
                }
                finish()
            } else{
                Log.e("Inserir produto", "Preencha com todas as informações!")
            }
        }

        binding.btnVoltar.setOnClickListener {
            finish()
        }
    }
}