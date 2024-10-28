package com.example.trabalho2pdm.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.sql.Date

@Entity(tableName = "Produto")
data class Produto (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nomeProduto: String,
    val descricao: String,
    val valor: Double
)


@Entity(tableName = "Usuario",
    indices = [Index(value = ["email"], unique = true)])
data class Usuario (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nomeUsuario: String,
    val email: String,
    val senha: String
)


@Entity(
    tableName = "Pedido",
    foreignKeys = [
        ForeignKey(
            entity = Pedido::class,
            parentColumns = ["id"],
            childColumns = ["produtoID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioID"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("produtoID"), Index("usuarioID")]
)
data class Pedido (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val produtoID: Int,
    val usuarioID: Int,
    val dataPedido: String
)
