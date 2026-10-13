package pe.edu.upc.mealscompose.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.mealscompose.data.local.SongDao
import pe.edu.upc.mealscompose.data.model.SongMapper
import pe.edu.upc.mealscompose.data.remote.SongService
import pe.edu.upc.mealscompose.domain.Song

class SongRepository(
    private val songService: SongService,
    private val songDao: SongDao
){
    // 1. Obtener canciones de la API y verificar al mismo tiempo si son favoritas en Room
    suspend fun getSongs(): List<Song> = withContext(Dispatchers.IO) {
        val response = songService.getSongs()
        if (response.isSuccessful) {
            val songsResponse = response.body()
            val results = songsResponse?.results ?: emptyList()

            return@withContext results.map { songResponse ->
                // Mapeamos de respuesta a dominio
                val song = SongMapper.toSong(songResponse)

                // Verificamos en Room si esta canción ya está guardada como favorita localmente
                val isFav = songDao.getFavoriteSongs().any { it.id == song.id }

                // Actualizamos el estado isFavorite al vuelo
                song.copy(isFavorite = isFav)
            }
        }
        return@withContext emptyList()
    }

    // 2. Obtener las canciones favoritas almacenadas localmente en Room
    suspend fun getFavoriteSongs(): List<Song> = withContext(Dispatchers.IO) {
        val entityList = songDao.getFavoriteSongs()
        return@withContext entityList.map { SongMapper.toSong(it) }
    }

    // 3. Agregar a favoritos usando Dispatchers.IO
    suspend fun insertFavorite(song: Song) = withContext(Dispatchers.IO) {
        val entity = SongMapper.toEntity(song)
        songDao.insertFavorite(entity)
    }

    // 4. Eliminar de favoritos usando Dispatchers.IO
    suspend fun deleteFavorite(song: Song) = withContext(Dispatchers.IO) {
        val entity = SongMapper.toEntity(song)
        songDao.deleteFavorite(entity)
    }
}
