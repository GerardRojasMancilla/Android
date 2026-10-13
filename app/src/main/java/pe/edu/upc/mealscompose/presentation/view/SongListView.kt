package pe.edu.upc.mealscompose.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import pe.edu.upc.mealscompose.domain.Song
import pe.edu.upc.mealscompose.presentation.viewmodel.SongListViewModel
import androidx.compose.foundation.lazy.items // <-- IMPORTANTE: Asegúrate de tener este import
import pe.edu.upc.mealscompose.presentation.di.PresentationModule

@Composable
fun SongListView(
    //Sin implementar lo di de presentation
    //viewModel: SongListViewModel,
    viewModel: SongListViewModel = PresentationModule.getSongListViewModel(),
    onSongClick: (Song) -> Unit = {}
) {
    // Disparamos la carga si fuera necesario y recolectamos el estado de las canciones
    val songs = viewModel.songs.collectAsState()

    LazyColumn(modifier = Modifier.padding(8.dp)) {
        items(songs.value) { song ->
            SongListItemView(
                song = song,
                onCardClick = { onSongClick(song) },
                toggleFavorite = {
                    // Llamamos directamente al ViewModel pasando el objeto song
                    viewModel.toggleFavorite(song)
                }
            )


        }
    }
}

@Composable
fun SongListItemView(
    song: Song,
    onCardClick: () -> Unit,
    toggleFavorite: () -> Unit // Ya no necesitamos pasar un booleano extra
) {


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box {
                AsyncImage(
                    model = song.image,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp),
                    contentScale = ContentScale.Crop
                )
                IconButton(
                    onClick = {
                        // Invocamos la acción; el ViewModel se encarga de invertirlo y actualizar el estado
                        toggleFavorite()
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                ) {
                    Icon(
                        // Leemos directamente la propiedad del objeto song (sin remember)
                        imageVector = if (song.isFavorite) {
                            Icons.Default.Favorite
                        } else {
                            Icons.Default.FavoriteBorder
                        },
                        contentDescription = null,
                        tint = if (song.isFavorite) Color.Red else Color.Gray
                    )
                }
            }
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = song.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Text(
                    text = "Artista: ${song.artist}",
                    maxLines = 1
                )
                Text(
                    text = "Álbum: ${song.album}",
                    maxLines = 1
                )
            }
        }
    }
}