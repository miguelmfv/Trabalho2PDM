package com.example.trabalho2pdm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.trabalho2pdm.data.dao.DAOUsuario
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.data.model.Usuario
import com.example.trabalho2pdm.databinding.EditProfileActivityBinding
import com.example.trabalho2pdm.databinding.ProfileActivityBinding
import kotlinx.coroutines.launch

class EditUserActivity : AppCompatActivity() {
    private lateinit var binding: EditProfileActivityBinding
    private lateinit var usuarioDAO: DAOUsuario
    private lateinit var user: Usuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
        val userId = sharedPreferences.getInt("user_id", -1)

        if (userId != -1) { //prosseguir com o app
            binding = EditProfileActivityBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val db = AppDatabase.getDatabase(this)
            usuarioDAO = db.usuarioDAO()

            lifecycleScope.launch {
                user = usuarioDAO.selectUsuarioID(userId)!!
            }

            binding.btnAplicarEditar.setOnClickListener {
                val nome = binding.fieldNameAlterar.text.toString()
                val email = binding.fieldEmailEditar.text.toString()
                val senha = binding.fieldPassEditar.text.toString()

                if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
                    user = Usuario(nomeUsuario = nome, email = email, senha = senha)
                    lifecycleScope.launch {
                        usuarioDAO.updateUsuario(user)
                    }
                } else {
                    Toast.makeText(this, "Por favor, preencha os campos acima!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            binding.btnCancelarEditar.setOnClickListener {
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("user_id", userId)
                startActivity(intent)
                finish()
            }
        }
    }
}