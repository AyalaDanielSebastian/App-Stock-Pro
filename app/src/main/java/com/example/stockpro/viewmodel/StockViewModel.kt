
package com.example.stockpro.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.stockpro.model.Producto

class StockViewModel : ViewModel() {

    val productos = mutableStateListOf(
        Producto(
            id = 1,
            nombre = "Taladro inalámbrico",
            descripcion = "Taladro de 20 V con batería recargable.",
            precio = 89.99,
            stockActual = 8
        ),
        Producto(
            id = 2,
            nombre = "Caja de tornillos",
            descripcion = "Caja surtida con 200 tornillos galvanizados.",
            precio = 14.50,
            stockActual = 3
        ),
        Producto(
            id = 3,
            nombre = "Guantes de seguridad",
            descripcion = "Guantes resistentes para trabajo industrial.",
            precio = 7.25,
            stockActual = 0
        ),
        Producto(
            id = 4,
            nombre = "Casco protector",
            descripcion = "Casco ajustable para trabajos en bodega.",
            precio = 22.80,
            stockActual = 12
        ),
        Producto(
            id = 5,
            nombre = "Cinta métrica",
            descripcion = "Cinta métrica reforzada de 5 metros.",
            precio = 6.75,
            stockActual = 4
        ),
        Producto(
            id = 6,
            nombre = "Linterna LED",
            descripcion = "Linterna recargable de alta potencia.",
            precio = 18.40,
            stockActual = 7
        )
    )

    fun obtenerProducto(id: Int): Producto? {
        return productos.find { producto -> producto.id == id }
    }

    fun actualizarStock(id: Int, nuevaCantidad: Int) {
        if (nuevaCantidad < 0) return

        val indice = productos.indexOfFirst { producto ->
            producto.id == id
        }

        if (indice != -1) {
            productos[indice] = productos[indice].copy(
                stockActual = nuevaCantidad
            )
        }
    }

    fun calcularValorTotalInventario(): Double {
        return productos.sumOf { producto ->
            producto.precio * producto.stockActual
        }
    }

    fun obtenerProductosEnRiesgo(): List<Producto> {
        return productos.filter { producto ->
            producto.stockActual < 5
        }
    }

    fun contarProductosSinStock(): Int {
        return productos.count { producto ->
            producto.stockActual == 0
        }
    }
}
