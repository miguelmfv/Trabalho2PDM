package com.example.trabalho2pdm

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val userId = sharedPref.getInt("user_session", -1)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = AppDatabase.getDatabase(this)
        usuarioDAO = db.usuarioDAO()
        produtoDAO = db.produtoDAO()

        // Carregar adapter
        GlobalScope.launch(Dispatchers.IO) {
            val produtos = produtoDAO.selectAllProdutos()

            adapter = ProductAdapter(this@MainActivity, produtos) // Use o novo adaptador


            binding.productListView.setOnItemClickListener { _, _, position, _ ->
                val selectedProduct = produtos[position]
                Log.d("MainActivity", "Produto selecionado ID: ${selectedProduct.idProduto}")
                val intent = Intent(this@MainActivity, DetailedProductActivity::class.java)
                intent.putExtra("product_id", selectedProduct.idProduto.toString())
                this@MainActivity.startActivity(intent)
            }
        }

        carregarProdutos()

        binding.btnPerfilMain.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLojaMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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
                    binding.productListView.adapter = adapter
                }
            }
        }
    }
}

