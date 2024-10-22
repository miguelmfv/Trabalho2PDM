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
                lifecycleScope.launch {
                    val user = usuarioDAO.selectUsuarioByEmailAndSenha(email)
                    if (user){

                    }
                }





                val usuario = Usuario(email = email, senha = password)
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