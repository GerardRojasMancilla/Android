package pe.edu.upc.mealscompose.data.model

import pe.edu.upc.mealscompose.domain.Song

object SongMapper {

    // 1. TRADUCTOR DE RED A DOMINIO:
    // Convierte la respuesta HTTP que viene de la API de Azure (SongResponse)
    // en un objeto de negocio limpio y puro (Song) para la aplicación.
    fun toSong(songResponse: SongResponse) : Song{
        return Song(
            id = songResponse.id ?: 0,
            title = songResponse.title ?: "",
            artist = songResponse.artist ?: "",
            album = songResponse.album ?: "",
            year = songResponse.year ?: 0,
            duration = songResponse.duration ?: 0,
            genre = songResponse.genre ?: "",
            rating = songResponse.rating ?: 0.0,
            image = songResponse.image ?: "",
            isFavorite = false
        )
    }

    // 2. TRADUCTOR DE BASE DE DATOS LOCAL A DOMINIO:
    // Convierte la entidad de Room (SongEntity) que estaba guardada
    // en el almacenamiento interno del celular en un objeto de dominio (Song).
    fun toSong(songEntity: SongEntity): Song {
        return Song(
            id = songEntity.id,
            title = songEntity.title,
            artist = songEntity.artist,
            album = songEntity.album,
            year = songEntity.year,
            duration = songEntity.duration,
            genre = songEntity.genre,
            rating = songEntity.rating,
            image = songEntity.image,
            isFavorite = true // Si viene de la tabla de favoritos local, por lógica ya es favorito
        )
    }

    // 3. TRADUCTOR DE DOMINIO A BASE DE DATOS LOCAL:
    // Convierte un objeto de dominio (Song) en una entidad de Room (SongEntity)
    // para que el DAO pueda guardarlo físicamente cuando el usuario presione el botón de favorito.
    fun toEntity(song: Song): SongEntity {
        return SongEntity(
            id = song.id,
            title = song.title,
            artist = song.artist,
            album = song.album,
            year = song.year,
            duration = song.duration,
            genre = song.genre,
            rating = song.rating,
            image = song.image
        )
    }




}