/*
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
import pe.edu.upc.mealscompose.presentation.view.CategoryListView
import pe.edu.upc.mealscompose.presentation.view.MealListview

@Composable
fun Homed() {
    val navController = rememberNavController()

    val selectedCategory = remember {
        mutableStateOf<String?>(null)
    }

    val selectedIndex = remember {
        mutableStateOf(0)
    }

    val navigationItems = listOf(
        NavigationItem(
            icon = Icons.Default.Home,
            title = "Categories",
            route = "categories"
        ),
        NavigationItem(
            icon = Icons.Default.Favorite,
            title = "Favorites",
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
                            navController.navigate(navigationItem.route)
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
            navController,
            startDestination = "categories",
            modifier = Modifier.padding(padding)
        ) {
            composable("categories") {
                CategoryListView { category ->
                    selectedCategory.value = category
                    navController.navigate("meals")
                }
            }
            composable("meals") {

                selectedCategory.value?.let { it ->
                    MealListview(it)

                }
            }

            composable("favorites") {
                Text("Favorites")
            }
        }
    }


}
/*
data class NavigationItem(
    val icon: ImageVector,
    val title: String,
    val route: String
)*/
        */