package com.example.trabalho2pdm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trabalho2pdm.data.model.Carrinho

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