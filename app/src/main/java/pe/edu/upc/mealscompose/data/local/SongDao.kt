package pe.edu.upc.mealscompose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pe.edu.upc.mealscompose.data.model.SongEntity

//ENTORNO SQL full


//El DAO (Data Access Object) es una interfaz que funciona como
// un intermediario directo entre tu código de Kotlin y la tabla de la base
// de datos SQLite (favorite_songs)
@Dao
interface SongDao {


    //getFavoriteSongs(): Ejecuta un SELECT * FROM favorite_songs para traerte
    // del almacenamiento interno todas las canciones que el usuario marcó
    // previamente como favoritas.
    @Query("SELECT * FROM favorite_song")
    suspend fun getFavoriteSongs(): List<SongEntity>

    //insertFavorite(song): Guarda una canción en la tabla local. Tiene configurada
    // la estrategia OnConflictStrategy.REPLACE, lo que significa que si intentas guardar
    // una canción que ya existía, Room la sobrescribirá en lugar de generar un error de
    // duplicidad.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(song: SongEntity)


    //deleteFavorite(song): Elimina una canción específica de la base de
    // datos local cuando el usuario decide quitarla de sus favoritos.
    @Delete
    suspend fun deleteFavorite(song: SongEntity)
}