package com.example.trabalho2pdm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.trabalho2pdm.adapter.PedidoAdapter
import com.example.trabalho2pdm.adapter.ProductAdapter
import com.example.trabalho2pdm.data.dao.DAOPedido
import com.example.trabalho2pdm.data.dao.DAOUsuario
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.databinding.ProfileActivityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileActivity: AppCompatActivity() {
    private lateinit var binding: ProfileActivityBinding
    private lateinit var usuarioDAO: DAOUsuario
    private lateinit var adapter: PedidoAdapter
    private lateinit var pedidosDAO: DAOPedido


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val userId = sharedPref.getInt("user_id", -1)
        val isAdmin = sharedPref.getBoolean("admin", false)

        val db = AppDatabase.getDatabase(this)
        usuarioDAO = db.usuarioDAO()
        pedidosDAO = db.pedidoDAO()

        if (userId != -1) { //prosseguir com o app
            lifecycleScope.launch {
                val user = usuarioDAO.selectUsuarioID(userId)
                user?.let {


                    carregarPedidos()


                    binding.ProfileUserName.text = "Olá ${user.nomeUsuario}!"

                    if (isAdmin) {
                        binding.btnAdmin.visibility = View.VISIBLE
                    }
                    binding.btnEditar.setOnClickListener {
                        Toast.makeText(this@ProfileActivity, "NÃO ESTÁ FUNCIONANDO!", Toast.LENGTH_SHORT).show()
                        //val intent = Intent(this@ProfileActivity, EditUserActivity::class.java)
                        //startActivity(intent)
                    }

                    binding.btnAdmin.setOnClickListener {
                        val intent = Intent(this@ProfileActivity, ProductsManagerActivity::class.java)
                        startActivity(intent)
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
    }

    private fun carregarPedidos() {
        GlobalScope.launch(Dispatchers.IO) {
            val pedidos = pedidosDAO.selectAllPedido()
            adapter = PedidoAdapter(this@ProfileActivity, pedidos) // Use o novo adaptador


            if (pedidos.isEmpty()){
                Log.e("Carregar Pedidos", "Nenhum Pedido encontrado!")
            } else {
                Log.d("Carregar Pedidos", "Pedidos carregados: ${pedidos.size}")
            }

            runOnUiThread {
                if (pedidos.isEmpty()) {
                    Toast.makeText(this@ProfileActivity, "Nenhum pedido encontrado", Toast.LENGTH_SHORT).show()
                } else {
                    binding.listViewPedidos.adapter = adapter
                }
            }
        }
    }

}