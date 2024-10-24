package com.example.trabalho2pdm

import android.annotation.SuppressLint
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

//VOU DEIXAR DESABILITADO NÃO ESTÁ FUNCIONANDO!!!!

class EditUserActivity : AppCompatActivity() {
    private lateinit var binding: EditProfileActivityBinding
    private lateinit var usuarioDAO: DAOUsuario
    private lateinit var user: Usuario

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getIntExtra("user_id", -1)

        if (userId != -1) { //prosseguir com o app
            binding = EditProfileActivityBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val db = AppDatabase.getDatabase(this)
            usuarioDAO = db.usuarioDAO()

            lifecycleScope.launch {
                user = usuarioDAO.selectUsuarioID(userId)

                binding.campoNome.text = "Seu nome é: ${user.nomeUsuario}"
                binding.campoEmail.text = "Seu email é: ${user.email}"
                binding.campoSenha.text = "Sua senha é: ${user.senha}"
            }

            binding.btnAplicarEditar.setOnClickListener {
                val nome = binding.fieldNameAlterar.text.toString()
                val email = binding.fieldEmailEditar.text.toString()
                val senha = binding.fieldPassEditar.text.toString()

                if (nome.isNotEmpty() || email.isNotEmpty() || senha.isNotEmpty()) {
                    user = Usuario(nomeUsuario = nome, email = email, senha = senha)
                    lifecycleScope.launch {
                        try {
                            usuarioDAO.updateUsuario(user)
                            Toast.makeText(this@EditUserActivity, "Usuário alterado!", Toast.LENGTH_SHORT).show()
                        } catch (e:Exception){
                            Toast.makeText(this@EditUserActivity, "Ocorreu algum problema!", Toast.LENGTH_LONG).show()
                            e.printStackTrace()
                        }
                    }
                } else {
                    Toast.makeText(this, "Por favor, preencha os campos acima!", Toast.LENGTH_SHORT)
                        .show()
                }
                val intent = Intent(this, ProfileActivity::class.java)
                intent.putExtra("user_id", userId)
                startActivity(intent)
                finish()
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