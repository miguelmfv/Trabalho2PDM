package com.example.trabalho2pdm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.trabalho2pdm.data.dao.DAOUsuario
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.data.model.Usuario
import com.example.trabalho2pdm.databinding.ProfileActivityBinding
import kotlinx.coroutines.launch

class ProfileActivity: AppCompatActivity() {
    private lateinit var binding: ProfileActivityBinding
    private lateinit var usuarioDAO: DAOUsuario
    private lateinit var user: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", -1)

        if (userId != -1) { //prosseguir com o app
            binding = ProfileActivityBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val db = AppDatabase.getDatabase(this)
            usuarioDAO = db.usuarioDAO()

            lifecycleScope.launch {
                user = usuarioDAO.selectUsuarioID(userId)!!
            }

            binding.ProfileUserName.text = user.nomeUsuario
            //configurar a parte onde mostra os pedidos do cliente


            binding.btnEditar.setOnClickListener {
                val intent = Intent(this, EditUserActivity::class.java)
                intent.putExtra("user_id", userId)
                startActivity(intent)
                finish()
            }

            binding.btnDeslogar.setOnClickListener {
                val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.clear()
                editor.apply()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnLojaProfile.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnCarrinhoProfile.setOnClickListener {
            //val intent = Intent(this, ProfileActivity::class.java)
            //startActivity(intent)
            //finish()
        }
    }
}