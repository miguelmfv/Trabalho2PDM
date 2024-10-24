package com.example.trabalho2pdm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.trabalho2pdm.adapter.ProdutoAdapter
import com.example.trabalho2pdm.data.dao.DAOProduto
import com.example.trabalho2pdm.data.dao.DAOUsuario
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listViewProducts: ListView
    private lateinit var produtoDAO: DAOProduto
    private lateinit var usuarioDAO: DAOUsuario
    private lateinit var adapter: ProdutoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val userId = sharedPref.getInt("user_session", -1)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = AppDatabase.getDatabase(this)
        usuarioDAO = db.usuarioDAO()

        adapter = ProdutoAdapter(
            this, listViewProducts
        )
        binding.productListView.adapter = adapter
        carregarProdutos()

        lifecycleScope.launch {
            val users = usuarioDAO.selectAllUsuario()
            Log.d("LoginActivity", "Usuários no banco: $users")
        }

        binding.btnPerfil.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        binding.btnCarrinho.setOnClickListener {
            //val intent = Intent(this, Activity::class.java)
            //startActivity(intent)
            //finish()
        }
    }

    private fun carregarProdutos() {
        lifecycleScope.launch {
            val produtos = withContext(Dispatchers.IO) {
                produtoDAO.selectAllProdutos()
            }
            listViewProducts.clear()
            listViewProducts.addAll(produtos)
            adapter.notifyDataSetChanged()
        }
    }
}

