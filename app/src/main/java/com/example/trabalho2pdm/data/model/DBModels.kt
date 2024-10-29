package com.example.trabalho2pdm.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Produto")
data class Produto(
    @PrimaryKey(autoGenerate = true) val idProduto: Int = 0,
    val nomeProduto: String,
    val descricao: String,
    val valor: Double
)


@Entity(tableName = "Usuario",
    indices = [Index(value = ["email"], unique = true)])
data class Usuario (
    @PrimaryKey(autoGenerate = true) val idUsuario: Int = 0,
    val nomeUsuario: String,
    val email: String,
    val senha: String
)


@Entity(
    tableName = "Pedido",
    foreignKeys = [
        ForeignKey(
            entity = Produto::class,
            parentColumns = ["idProduto"],
            childColumns = ["produtoID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["idUsuario"],
            childColumns = ["usuarioID"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("produtoID"), Index("usuarioID")]
)
data class Pedido (
    @PrimaryKey(autoGenerate = true) val idPedido: Int = 0,
    val produtoID: Int,
    val usuarioID: Int,
    val dataPedido: String
)
