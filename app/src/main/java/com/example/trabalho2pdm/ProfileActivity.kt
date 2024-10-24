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
import com.example.trabalho2pdm.databinding.ProfileActivityBinding
import kotlinx.coroutines.launch

class ProfileActivity: AppCompatActivity() {
    private lateinit var binding: ProfileActivityBinding
    private lateinit var usuarioDAO: DAOUsuario

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val userId = sharedPref.getInt("user_session", -1)

        val db = AppDatabase.getDatabase(this)
        usuarioDAO = db.usuarioDAO()

        if (userId != -1) { //prosseguir com o app
            lifecycleScope.launch {
                val user = usuarioDAO.selectUsuarioID(userId)
                user?.let {


                    // Configurar a parte onde mostra os pedidos do cliente


                    binding.ProfileUserName.text = user.nomeUsuario


                    binding.btnEditar.setOnClickListener {

                        Toast.makeText(this@ProfileActivity, "NÃO ESTÁ FUNCIONANDO!", Toast.LENGTH_SHORT).show()
                        //val intent = Intent(this@ProfileActivity, EditUserActivity::class.java)
                        //startActivity(intent)
                    }
                    binding.btnDeslogar.setOnClickListener {
                        val sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.clear()
                        editor.apply()
                        Toast.makeText(this@ProfileActivity, "Saindo!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }else {
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