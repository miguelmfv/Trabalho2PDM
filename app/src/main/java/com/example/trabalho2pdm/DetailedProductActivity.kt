package com.example.trabalho2pdm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.trabalho2pdm.data.dao.DAOPedido
import com.example.trabalho2pdm.data.dao.DAOProduto
import com.example.trabalho2pdm.data.database.AppDatabase
import com.example.trabalho2pdm.data.model.Pedido
import com.example.trabalho2pdm.data.model.Produto
import com.example.trabalho2pdm.databinding.DetailProductActivityBinding
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DetailedProductActivity: AppCompatActivity() {
    private lateinit var binding: DetailProductActivityBinding
    private lateinit var product: Produto
    private lateinit var order: Pedido
    private lateinit var produtoDAO: DAOProduto
    private lateinit var pedidoDAO: DAOPedido

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DetailProductActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productID = intent.getStringExtra("product_id")
        val sharedPref = getSharedPreferences("user_session", MODE_PRIVATE)
        val userId = sharedPref.getInt("user_id", -1)

        val db = AppDatabase.getDatabase(this)

        val idProd = productID!!.toInt()

        produtoDAO = db.produtoDAO()
        pedidoDAO = db.pedidoDAO()
        try {
            lifecycleScope.launch {
                product = produtoDAO.selectProdutoID(idProd)!!

                binding.fieldProductDetailedName.text = product.nomeProduto
                binding.fieldProductDetailedPrice.text = product.valor.toString()
                binding.productDescription.text = product.descricao
                binding.btnBuy.setOnClickListener {
                    if (userId != -1){
                        val dataCompra = getCurrentDateTimeAsString()
                        Log.d("DetailedProductActivity", "Product ID: $product.id, User ID: $userId")


                        order = Pedido(produtoID = product.idProduto, usuarioID = userId, dataPedido = dataCompra)

                        try {
                            lifecycleScope.launch {
                                pedidoDAO.insertPedido(order)
                                Toast.makeText(this@DetailedProductActivity, "Você realizou a compra! Muito obrigado!", Toast.LENGTH_LONG).show()
                                finish()
                            }
                        } catch (e:Exception) {
                            Toast.makeText(this@DetailedProductActivity, "Não foi possível fazer a Compra!", Toast.LENGTH_LONG).show()
                            e.printStackTrace()
                        }
                    } else {
                        Toast.makeText(this@DetailedProductActivity, "Você precisa estar Logado!!", Toast.LENGTH_LONG).show()
                        val intent = Intent(this@DetailedProductActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        } catch (e:Exception) {
            Toast.makeText(this, "Produto ${product.nomeProduto} não encontrado!", Toast.LENGTH_LONG).show()
            e.printStackTrace()
            finish()
        }

        binding.btnLojaProduct.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnPerfilProduct.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
    }



    private fun getCurrentDateTimeAsString(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
        return currentDateTime.format(formatter)
    }
}