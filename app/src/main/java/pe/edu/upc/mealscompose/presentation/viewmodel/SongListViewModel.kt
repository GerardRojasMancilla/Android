package pe.edu.upc.mealscompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.mealscompose.data.repository.SongRepository
import pe.edu.upc.mealscompose.domain.Song

class SongListViewModel (private val songRepository: SongRepository): ViewModel() {
    // 1. Estado reactivo para la lista general de canciones
    private val _songs = MutableStateFlow<List<Song>>(emptyList())
    val songs: StateFlow<List<Song>> = _songs

    // 2. Estado reactivo para las canciones favoritas
    private val _favoriteSongs = MutableStateFlow<List<Song>>(emptyList())
    val favoriteSongs: StateFlow<List<Song>> = _favoriteSongs

    // 3. Estado para la canción seleccionada (detalle)
    private val _selectedSong = MutableStateFlow<Song?>(null)
    val selectedSong: StateFlow<Song?> = _selectedSong

    init {
        fetchSongs()
        fetchFavoriteSongs()
    }

    // Obtener todas las canciones de la API
    fun fetchSongs() {
        viewModelScope.launch {
            _songs.value = songRepository.getSongs()
        }
    }

    // Obtener las canciones favoritas de Room
    fun fetchFavoriteSongs() {
        viewModelScope.launch {
            _favoriteSongs.value = songRepository.getFavoriteSongs()
        }
    }

    // Seleccionar una canción para la pantalla de detalle
    fun selectSong(song: Song) {
        _selectedSong.value = song
    }

    // Alternar favorito (guardar o eliminar)
    fun toggleFavorite(song: Song) {
        viewModelScope.launch(Dispatchers.IO) {
            // 1. Cambiamos el estado booleano en el objeto
            val newFavoriteState = !song.isFavorite
            song.isFavorite = newFavoriteState

            // 2. Guardamos o eliminamos en la base de datos local (Room)
            if (newFavoriteState) {
                songRepository.insertFavorite(song)
            } else {
                songRepository.deleteFavorite(song)
            }

            // 3. Actualizamos la lista local en memoria creando una nueva instancia
            // para que Compose detecte el cambio y repinte los corazones sin llamar a la API
            val currentList = _songs.value.map {
                if (it.id == song.id) song else it
            }
            _songs.value = currentList

            // 4. Refrescamos la lista de favoritos por si el usuario cambia de pestaña
            fetchFavoriteSongs()
        }
    }

    // Eliminar desde la vista de favoritos
    fun removeFavorite(song: Song) {
        viewModelScope.launch {
            songRepository.deleteFavorite(song)
            fetchFavoriteSongs()
            fetchSongs()
        }
    }
}