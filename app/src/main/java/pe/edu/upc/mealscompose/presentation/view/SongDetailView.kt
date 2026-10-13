package pe.edu.upc.mealscompose.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import pe.edu.upc.mealscompose.presentation.viewmodel.SongListViewModel

@Composable
fun SongDetailView(
    viewModel: SongListViewModel,
    onBack: () -> Unit
) {
    val song = viewModel.selectedSong.collectAsState().value

    if (song == null) {
        Text("No se ha seleccionado ninguna canción", modifier = Modifier.padding(16.dp))
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = song.image,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(song.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text("Artista: ${song.artist}", fontSize = 18.sp)
        Text("Álbum: ${song.album}", fontSize = 16.sp)
        Text("Año: ${song.year}", fontSize = 16.sp)
        Text("Duración: ${song.duration}", fontSize = 16.sp)
        Text("Género: ${song.genre}", fontSize = 16.sp)
        Text("Calificación: ${song.rating}", fontSize = 16.sp)

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para marcar o desmarcar como favorita desde el detalle
        Button(
            onClick = { viewModel.toggleFavorite(song) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (song.isFavorite) "Quitar de Favoritos" else "Marcar como Favorita")
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedButton(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver")
        }
    }
}
