package pe.edu.upc.mealscompose.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import pe.edu.upc.mealscompose.presentation.viewmodel.SongListViewModel
import androidx.compose.foundation.lazy.items // <-- IMPORTANTE: Asegúrate de tener este import
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight

@Composable
fun FavoriteSongsView(viewModel: SongListViewModel) {
    // Refrescamos y recolectamos las canciones favoritas almacenadas localmente en Room

    /*
    Esto es por si hay algun bublce infinito llamandose
    * androidx.compose.runtime.LaunchedEffect(Unit) {
        viewModel.fetchFavoriteSongs()
    }
    * */
    viewModel.fetchFavoriteSongs()
    val favoriteSongs = viewModel.favoriteSongs.collectAsState()

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(favoriteSongs.value) { song ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = song.image,
                        contentDescription = null,
                        modifier = Modifier.size(96.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                    ) {
                        Text(text = song.title, fontWeight = FontWeight.Bold)
                        Text(text = "Artista: ${song.artist}")
                        Text(text = "Género: ${song.genre}")
                    }
                    IconButton(onClick = {
                        viewModel.removeFavorite(song)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Eliminar favorito",
                            tint = androidx.compose.ui.graphics.Color.Red
                        )
                    }
                }
            }
        }
    }
}