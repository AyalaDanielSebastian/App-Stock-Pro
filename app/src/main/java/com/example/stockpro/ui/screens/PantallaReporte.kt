package com.example.stockpro.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import java.text.NumberFormat
import java.util.Locale

@Composable
fun PantallaReporte(
    stockViewModel: StockViewModel,
    onVolverCatalogo: () -> Unit
) {
    // Los cálculos pertenecen al ViewModel, no a la Vista.
    val valorTotal = stockViewModel.calcularValorTotalInventario()
    val productosSinStock = stockViewModel.contarProductosSinStock()

    Scaffold(
        topBar = {
            BarraStockPro(titulo = "Reporte Financiero")
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Capital Invertido Total",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = formatearCapital(valorTotal),
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    Text(
                        text = "Total de productos con stock en cero",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(
                        text = productosSinStock.toString(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (productosSinStock > 0) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onVolverCatalogo,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al Catálogo")
            }
        }
    }
}

private fun formatearCapital(valor: Double): String {
    return NumberFormat
        .getCurrencyInstance(Locale.US)
        .format(valor)
}

