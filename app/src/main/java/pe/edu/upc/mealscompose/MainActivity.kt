package pe.edu.upc.mealscompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import pe.edu.upc.mealscompose.presentation.navigation.Home
import pe.edu.upc.mealscompose.ui.theme.SongAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SongAppTheme {
                //dos formas asi uno solo Home() o la otra
                //Home(viewModel = PresentationModule.getSongListViewModel())
                Home()// Llama a la navegación central de la app
            }
        }
    }
}