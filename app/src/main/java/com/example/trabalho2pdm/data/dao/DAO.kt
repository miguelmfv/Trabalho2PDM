package com.example.trabalho2pdm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trabalho2pdm.data.model.Carrinho
import com.example.trabalho2pdm.data.model.CarrinhoProduto
import com.example.trabalho2pdm.data.model.Usuario
import com.example.trabalho2pdm.data.model.Pedido
import com.example.trabalho2pdm.data.model.Produto

@Dao
interface DAOProduto {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduto(produto: Produto)

    @Query("SELECT * FROM Produto")
    suspend fun selectAllProdutos(): List<Produto>

    @Query("SELECT * FROM Produto WHERE id = :id")
    suspend fun selectProdutoID(id: Int): Produto?

    @Update
    suspend fun updateProduto(produto: Produto)

    @Delete
    suspend fun deleteProduto(produto: Produto)
}

@Dao
interface DAOCarrinho {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarrinho(carrinho: Carrinho)

    @Query("SELECT * FROM Carrinho")
    suspend fun selectAllCarrinho(): List<Carrinho>

    @Query("SELECT * FROM Carrinho WHERE id = :id")
    suspend fun selectCarrinhoID(id: Int): Carrinho?

    @Update
    suspend fun updateCarrinho(carrinho: Carrinho)

    @Delete
    suspend fun deleteCarrinho(carrinho: Carrinho)
}

@Dao
interface DAOUsuario {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: Usuario)

    @Query("SELECT * FROM Usuario")
    suspend fun selectAllUsuario(): List<Usuario>

    @Query("SELECT * FROM Usuario WHERE email = :email")
    suspend fun selectUsuarioByEmailAndSenha(email: String): Usuario?

    @Query("SELECT * FROM Usuario WHERE id = :id")
    suspend fun selectUsuarioID(id: Int): Usuario?

    @Update
    suspend fun updateUsuario(usuario: Usuario)

    @Delete
    suspend fun deleteUsuario(usuario: Usuario)
}

@Dao
interface DAOPedido {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPedido(pedido: Pedido)

    @Query("SELECT * FROM Pedido")
    suspend fun selectAllPedido(): List<Pedido>

    @Query("SELECT * FROM Pedido WHERE id = :id")
    suspend fun selectPedidoID(id: Int): Pedido?

    @Update
    suspend fun updatePedido(pedido: Pedido)

    @Delete
    suspend fun deletePedido(pedido: Pedido)
}

@Dao
interface DAOCarrinhoProduto {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCarrinhoProduto(carrinhoProduto: CarrinhoProduto)

    @Query("SELECT * FROM CarrinhoProduto")
    suspend fun selectAllCarrinhoProduto(): List<CarrinhoProduto>

    @Query("SELECT * FROM CarrinhoProduto WHERE id = :id")
    suspend fun selectCarrinhoProdutoID(id: Int): CarrinhoProduto?

    @Update
    suspend fun updateCarrinhoProduto(carrinhoProduto: CarrinhoProduto)

    @Delete
    suspend fun deleteCarrinhoProduto(carrinhoProduto: CarrinhoProduto)
}