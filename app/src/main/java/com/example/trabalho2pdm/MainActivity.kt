package com.example.trabalho2pdm

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trabalho2pdm.data.dao.DAOProduto
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listViewProducts: ListView
    private lateinit var produtoDAO: DAOProduto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listViewProducts = binding.productListView
        val db = AppDatabase.getDatabase(this)
        produtoDAO = db.produtoDAO()
    }
}