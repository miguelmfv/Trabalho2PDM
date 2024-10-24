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