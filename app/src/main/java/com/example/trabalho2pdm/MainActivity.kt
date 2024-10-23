package com.example.trabalho2pdm

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.trabalho2pdm.data.dao.DAOProduto
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.databinding.ActivityMainBinding
import com.example.trabalho2pdm.databinding.ProfileActivityBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var listViewProducts: ListView
    private lateinit var produtoDAO: DAOProduto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", -1)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPerfil.setOnClickListener {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
                finish()
        }
        binding.btnCarrinho.setOnClickListener {
            //val intent = Intent(this, Activity::class.java)
            //startActivity(intent)
            //finish()
        }
    }

}

