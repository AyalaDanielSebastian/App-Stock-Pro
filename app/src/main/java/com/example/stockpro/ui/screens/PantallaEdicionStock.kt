
package com.example.stockpro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.weight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stockpro.ui.components.BarraStockPro
import com.example.stockpro.viewmodel.StockViewModel

@Composable
fun PantallaEdicionStock(
    productoId: Int,
    stockViewModel: StockViewModel,
    onGuardarYVolver: () -> Unit
) {
    // Al reemplazarse un producto en mutableStateListOf, esta lectura se recompone.
    val producto = stockViewModel.obtenerProducto(productoId)

    Scaffold(
        topBar = {
            BarraStockPro(titulo = "Edición de Stock")
        }
    ) { paddingValues ->
        if (producto == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Producto no encontrado.",
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = onGuardarYVolver) {
                    Text("Volver")
                }
            }

            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = producto.nombre,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = producto.descripcion,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Stock actual",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Text(
                text = producto.stockActual.toString(),
                fontSize = 52.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        stockViewModel.actualizarStock(
                            id = producto.id,
                            nuevaCantidad = producto.stockActual - 1
                        )
                    },
                    enabled = producto.stockActual > 0,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("-1")
                }

                Button(
                    onClick = {
                        stockViewModel.actualizarStock(
                            id = producto.id,
                            nuevaCantidad = producto.stockActual + 1
                        )
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("+1")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onGuardarYVolver,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar y Volver")
            }
        }
    }
}
