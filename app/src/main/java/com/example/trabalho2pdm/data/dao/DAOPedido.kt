package com.example.trabalho2pdm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trabalho2pdm.data.model.Pedido

@Dao
interface DAOPedido {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPedido(pedido: Pedido)

    //@Query(""" SELECT * FROM Pedido
    //    INNER JOIN Usuario ON usuarioID = Usuario.id
    //    INNER JOIN Produto ON produtoID = Produto.id
    //    WHERE Pedido.usuarioID == :userID
    //        """)
    //suspend fun selectAllPedido(userID: Int): List<Pedido>

    @Query("SELECT * FROM Pedido WHERE id = :id")
    suspend fun selectPedidoID(id: Int): Pedido?

    @Delete
    suspend fun deletePedido(pedido: Pedido)
}