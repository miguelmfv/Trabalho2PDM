package com.example.trabalho2pdm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.trabalho2pdm.data.dao.DAOUsuario
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.data.model.Usuario
import com.example.trabalho2pdm.databinding.RegisterActivityBinding
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: RegisterActivityBinding
    private lateinit var usuarioDAO: DAOUsuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RegisterActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDatabase.getDatabase(this)
        usuarioDAO = db.usuarioDAO()

        binding.btnRegister.setOnClickListener {
            val username = binding.fieldNameRegistro.text.toString()
            val email = binding.fieldEmailRegistro.text.toString()
            val password = binding.fieldPassRegistro.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                val usuario = Usuario(nomeUsuario = username, email = email, senha = password)
                lifecycleScope.launch {
                    usuarioDAO.insertUsuario(usuario)
                    binding.fieldNameRegistro.text.clear()
                    binding.fieldEmailRegistro.text.clear()
                    binding.fieldPassRegistro.text.clear()
                    Toast.makeText(this@RegisterActivity, "Usuário adicionado", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_LONG).show()
            }
        }
    }
}