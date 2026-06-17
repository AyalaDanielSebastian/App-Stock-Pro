package com.example.stockpro.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.stockpro.model.Producto
import com.example.stockpro.ui.components.BarraStockPro
import com.example.stockpro.viewmodel.StockViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PantallaCatalogo(
    nombreOperario: String,
    stockViewModel: StockViewModel,
    onProductoSeleccionado: (Int) -> Unit,
    onAbrirReporte: () -> Unit
) {
    var mostrarSoloCriticos by rememberSaveable {
        mutableStateOf(false)
    }

    // Ambas opciones leen la lista reactiva del ViewModel.
    val productosVisibles = if (mostrarSoloCriticos) {
        stockViewModel.obtenerProductosEnRiesgo()
    } else {
        stockViewModel.productos
    }

    Scaffold(
        topBar = {
            BarraStockPro(
                titulo = "Catálogo de Inventario",
                subtitulo = "Operario: $nombreOperario"
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAbrirReporte
            ) {
                Text(
                    text = "Reporte",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (!mostrarSoloCriticos) {
                    Button(
                        onClick = {
                            mostrarSoloCriticos = false
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Ver Todo")
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            mostrarSoloCriticos = false
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Ver Todo")
                    }
                }

                if (mostrarSoloCriticos) {
                    Button(
                        onClick = {
                            mostrarSoloCriticos = true
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Stock Crítico")
                    }
                } else {
                    OutlinedButton(
                        onClick = {
                            mostrarSoloCriticos = true
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Stock Crítico")
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(
                    items = productosVisibles,
                    key = { producto -> producto.id }
                ) { producto ->
                    TarjetaProducto(
                        producto = producto,
                        onClick = {
                            onProductoSeleccionado(producto.id)
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(90.dp))
                }
            }
        }
    }
}

@Composable
private fun TarjetaProducto(
    producto: Producto,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = producto.nombre,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Precio unitario: ${formatearDinero(producto.precio)}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Stock actual: ${producto.stockActual}",
                color = if (producto.stockActual < 5) {
                    MaterialTheme.colorScheme.error
                } else {
                    LocalContentColor.current
                },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (producto.stockActual < 5) {
                    FontWeight.Bold
                } else {
                    FontWeight.Normal
                }
            )
        }
    }
}

private fun formatearDinero(valor: Double): String {
    return NumberFormat
        .getCurrencyInstance(Locale.US)
        .format(valor)
}

