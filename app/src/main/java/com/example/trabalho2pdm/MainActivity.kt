package com.example.trabalho2pdm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho2pdm.adapter.ProductAdapter
import com.example.trabalho2pdm.data.dao.DAOProduto
import com.example.trabalho2pdm.data.dao.DAOUsuario
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.data.model.Produto
import com.example.trabalho2pdm.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listViewProducts: ListView
    private lateinit var produtoDAO: DAOProduto
    private lateinit var usuarioDAO: DAOUsuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val userId = sharedPref.getInt("user_session", -1)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = AppDatabase.getDatabase(this)
        usuarioDAO = db.usuarioDAO()
        produtoDAO = db.produtoDAO()
        inserirProdutosExemplo()
        carregarProdutos()

        binding.btnPerfil.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun inserirProdutosExemplo() {
        GlobalScope.launch(Dispatchers.IO) {
            val produtosExemplo = listOf(
                Produto(nomeProduto = "Camiseta Esportiva", descricao = "Camiseta com tecido respirável.", valor = 49.99),
                Produto(nomeProduto = "Fone de Ouvido", descricao = "Fone de ouvido com cancelamento de ruído.", valor = 199.99),
                Produto(nomeProduto = "Mouse Gamer", descricao = "Mouse com 6 botões e DPI ajustável.", valor = 129.99),
                Produto(nomeProduto = "Teclado Mecânico", descricao = "Teclado RGB com switches mecânicos.", valor = 299.99),
                Produto(nomeProduto = "Cadeira Gamer", descricao = "Cadeira ergonômica com ajuste de altura.", valor = 599.99)
            )

            produtosExemplo.forEach { produto ->
                produtoDAO.insertProduto(produto)
            }
        }
    }

    private fun carregarProdutos() {
        GlobalScope.launch(Dispatchers.IO) {
            val produtos = produtoDAO.selectAllProdutos()

            if (produtos.isEmpty()){
                Log.e("Carregar produtos", "Nenhum produto encontrado!")
            } else {
                Log.d("Carregar produtos", "Produtos carregados: ${produtos.size}")
            }

            runOnUiThread {
                if (produtos.isEmpty()) {
                    Toast.makeText(this@MainActivity, "Nenhum produto encontrado", Toast.LENGTH_SHORT).show()
                } else {
                    val adapter = ProductAdapter(this@MainActivity, produtos) // Use o novo adaptador
                    binding.productListView.adapter = adapter
                }
            }
        }
    }
}

