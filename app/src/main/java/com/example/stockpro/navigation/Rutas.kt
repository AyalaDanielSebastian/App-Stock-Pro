package com.example.stockpro.navigation

import android.net.Uri

object Rutas {
    const val INGRESO = "ingreso"

    const val CATALOGO = "catalogo/{nombreOperario}"

    fun crearRutaCatalogo(nombreOperario: String): String {
        return "catalogo/${Uri.encode(nombreOperario.trim())}"
    }

    const val EDICION_STOCK = "edicion/{productoId}"

    fun crearRutaEdicion(productoId: Int): String {
        return "edicion/$productoId"
    }

    const val REPORTE = "reporte"
}
