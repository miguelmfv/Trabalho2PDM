package com.example.trabalho2pdm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.trabalho2pdm.data.dao.DAOUsuario
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.data.model.Usuario
import com.example.trabalho2pdm.databinding.LoginActivityBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: LoginActivityBinding
    private lateinit var usuarioDAO: DAOUsuario
    private lateinit var user: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val userId = sharedPref.getInt("user_session", -1)

        val db = AppDatabase.getDatabase(this)
        usuarioDAO = db.usuarioDAO()

        binding.btnLogin.setOnClickListener {
            val email = binding.fieldEmailLogin.text.toString()
            val password = binding.fieldPassLogin.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        user = usuarioDAO.selectUsuarioByEmail(email)

                        if (user.senha == password) {
                            Toast.makeText(this@LoginActivity, "Fazendo login!", Toast.LENGTH_LONG).show()
                            val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putInt("user_id", user.id)
                            editor.putBoolean("admin", user.nomeUsuario == "admin")
                            editor.apply()

                            val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
                            intent.putExtra("user_id", user.id)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Senha incorreta!", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(this@LoginActivity, "Usuário não encontrado!", Toast.LENGTH_LONG).show()
                        e.printStackTrace()
                    }
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnRegisterLogin.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnPerfil.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("user_id", userId)
            startActivity(intent)
        }
    }
}