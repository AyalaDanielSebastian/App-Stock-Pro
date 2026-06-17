package com.example.stockpro.model

data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val stockActual: Int
)
