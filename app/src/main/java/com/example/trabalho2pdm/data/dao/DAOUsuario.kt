package com.example.trabalho2pdm.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.trabalho2pdm.data.model.Usuario

@Dao
interface DAOUsuario {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE email = :email")
    suspend fun selectUsuarioByEmail(email: String): Usuario

    @Query("SELECT * FROM Usuario WHERE id = :id")
    suspend fun selectUsuarioID(id: Int): Usuario

    @Delete
    suspend fun deleteUsuario(usuario: Usuario)
}