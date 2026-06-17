package com.example.stockpro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxSize
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stockpro.navigation.StockProNavHost
import com.example.stockpro.viewmodel.StockViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Se crea una sola instancia compartida por todas las pantallas.
                    val stockViewModel: StockViewModel = viewModel()

                    StockProNavHost(
                        stockViewModel = stockViewModel
                    )
                }
            }
        }
    }
}
