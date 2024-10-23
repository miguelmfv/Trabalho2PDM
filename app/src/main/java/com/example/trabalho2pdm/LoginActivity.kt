package com.example.trabalho2pdm

import android.content.Intent
import android.os.Bundle
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

        val db = AppDatabase.getDatabase(this)
        usuarioDAO = db.usuarioDAO()

        binding.btnLogin.setOnClickListener {
            val email = binding.fieldEmailLogin.text.toString()
            val password = binding.fieldPassLogin.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                try {
                    lifecycleScope.launch {
                        user = usuarioDAO.selectUsuarioByEmail(email)!!

                        if (user.senha == password) {
                            val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putInt("user_id", user.id)
                            editor.apply()
                        } else {
                            Toast.makeText(this@LoginActivity, "Email ou senha incorretos!", Toast.LENGTH_LONG).show()
                        }
                    }
                    val intent = Intent(this, ProfileActivity::class.java)
                    intent.putExtra("user_id", user.id)
                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@LoginActivity, "Ocorreu um erro durante o login. Tente novamente.", Toast.LENGTH_LONG).show()
                    e.printStackTrace()
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnRegisterLogin.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}