package pe.edu.upc.mealscompose.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//Esto es la tabla del SQL
@Entity (tableName = "favorite_song")
data class SongEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val artist: String,
    val album: String,
    val year: Int,
    val duration: Int,
    val genre: String,
    val rating: Double,
    val image: String
)
