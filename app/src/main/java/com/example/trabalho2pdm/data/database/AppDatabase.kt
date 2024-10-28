package com.example.trabalho2pdm.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trabalho2pdm.data.dao.DAOUsuario
import com.example.trabalho2pdm.data.dao.DAOPedido
import com.example.trabalho2pdm.data.dao.DAOProduto
import com.example.trabalho2pdm.data.model.Usuario
import com.example.trabalho2pdm.data.model.Pedido
import com.example.trabalho2pdm.data.model.Produto


@Database(entities = [Produto::class, Usuario::class, Pedido::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun produtoDAO(): DAOProduto
    abstract fun usuarioDAO(): DAOUsuario
    abstract fun pedidoDAO(): DAOPedido

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Database$context"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}