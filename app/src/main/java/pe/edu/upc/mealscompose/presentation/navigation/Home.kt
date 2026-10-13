package pe.edu.upc.mealscompose.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pe.edu.upc.mealscompose.presentation.di.PresentationModule
import pe.edu.upc.mealscompose.presentation.view.FavoriteSongsView
import pe.edu.upc.mealscompose.presentation.view.SongDetailView

import pe.edu.upc.mealscompose.presentation.view.SongListView
import pe.edu.upc.mealscompose.presentation.viewmodel.SongListViewModel

@Composable
fun Home(
    viewModel: SongListViewModel = PresentationModule.getSongListViewModel()
) {
    val navController = rememberNavController()

    val selectedIndex = remember {
        mutableStateOf(0)
    }

    val navigationItems = listOf(
        NavigationItem(
            icon = Icons.Default.Home,
            title = "Canciones",
            route = "songs"
        ),
        NavigationItem(
            icon = Icons.Default.Favorite,
            title = "Favoritos",
            route = "favorites"
        )
    )

    Scaffold(
        bottomBar = {
            BottomAppBar {
                navigationItems.forEachIndexed { index, navigationItem ->
                    NavigationBarItem(
                        selected = selectedIndex.value == index,
                        onClick = {
                            selectedIndex.value = index
                            navController.navigate(navigationItem.route) {
                                // Evita acumular demasiadas pantallas en la pila al cambiar de pestaña
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(navigationItem.icon, contentDescription = navigationItem.title)
                        },
                        label = {
                            Text(navigationItem.title)
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "songs",
            modifier = Modifier.padding(padding)
        ) {
            // Pestaña 1: Lista de Canciones generales (API)
            composable("songs") {
                SongListView(viewModel = viewModel) { song ->
                    // Guardamos la canción seleccionada en el ViewModel y navegamos al detalle
                    viewModel.selectSong(song)
                    navController.navigate("detail")
                }
            }

            // Pantalla de Detalle de la Canción
            composable("detail") {
                SongDetailView(
                    viewModel = viewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // Pestaña 2: Canciones Favoritas (Room)
            composable("favorites") {
                FavoriteSongsView(viewModel = viewModel)
            }
        }
    }
}

data class NavigationItem(
    val icon: ImageVector,
    val title: String,
    val route: String
)