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

@Entity(
    tableName = "Carrinho",
    foreignKeys = [
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CarrinhoProduto::class,
            parentColumns = ["id"],
            childColumns = ["produtoID"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("usuarioID"), Index("produtoID")]
)
data class Carrinho (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuarioID: Int,
    val produtoID: Int,
    val precoTot: Double
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
            entity = Carrinho::class,
            parentColumns = ["id"],
            childColumns = ["carrinhoID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Usuario::class,
            parentColumns = ["id"],
            childColumns = ["usuarioID"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("carrinhoID"), Index("usuarioID")]
)
data class Pedido (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val carrinhoID: Int,
    val usuarioID: Int,
    val dataPedido: String
)

@Entity(
    tableName = "CarrinhoProduto",
    foreignKeys = [
        ForeignKey(
            entity = Produto::class,
            parentColumns = ["id"],
            childColumns = ["produtoID"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Carrinho::class,
            parentColumns = ["id"],
            childColumns = ["carrinhoID"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("produtoID"), Index("carrinhoID")]
)
data class CarrinhoProduto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val produtoID: Int,
    val carrinhoID: Int
)