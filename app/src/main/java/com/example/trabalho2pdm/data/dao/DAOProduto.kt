package com.example.trabalho2pdm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trabalho2pdm.data.model.Produto

@Dao
interface DAOProduto {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduto(produto: Produto)

    @Query("SELECT * FROM Produto")
    suspend fun selectAllProdutos(): List<Produto>

    @Query("SELECT * FROM Produto WHERE id = :id")
    suspend fun selectProdutoID(id: Int): Produto?

    @Delete
    suspend fun deleteProduto(produto: Produto)
}