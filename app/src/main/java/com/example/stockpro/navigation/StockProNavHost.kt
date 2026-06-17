package com.example.stockpro.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.stockpro.ui.screens.PantallaCatalogo
import com.example.stockpro.ui.screens.PantallaEdicionStock
import com.example.stockpro.ui.screens.PantallaIngreso
import com.example.stockpro.ui.screens.PantallaReporte
import com.example.stockpro.viewmodel.StockViewModel

@Composable
fun StockProNavHost(
    stockViewModel: StockViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.INGRESO,
        modifier = modifier
    ) {
        composable(route = Rutas.INGRESO) {
            PantallaIngreso(
                onIngresar = { nombreOperario ->
                    navController.navigate(
                        Rutas.crearRutaCatalogo(nombreOperario)
                    )
                }
            )
        }

        composable(
            route = Rutas.CATALOGO,
            arguments = listOf(
                navArgument("nombreOperario") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val nombreOperario = backStackEntry.arguments
                ?.getString("nombreOperario")
                .orEmpty()

            PantallaCatalogo(
                nombreOperario = nombreOperario,
                stockViewModel = stockViewModel,
                onProductoSeleccionado = { productoId ->
                    navController.navigate(
                        Rutas.crearRutaEdicion(productoId)
                    )
                },
                onAbrirReporte = {
                    navController.navigate(Rutas.REPORTE)
                }
            )
        }

        composable(
            route = Rutas.EDICION_STOCK,
            arguments = listOf(
                navArgument("productoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val productoId = backStackEntry.arguments
                ?.getInt("productoId")
                ?: -1

            PantallaEdicionStock(
                productoId = productoId,
                stockViewModel = stockViewModel,
                onGuardarYVolver = {
                    navController.popBackStack()
                }
            )
        }

        composable(route = Rutas.REPORTE) {
            PantallaReporte(
                stockViewModel = stockViewModel,
                onVolverCatalogo = {
                    navController.popBackStack()
                }
            )
        }
    }
}
