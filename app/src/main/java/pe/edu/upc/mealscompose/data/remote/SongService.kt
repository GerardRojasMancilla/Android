package pe.edu.upc.mealscompose.data.remote

// import okhttp3.Response
//tener cuidado aqui ya que eso no tiene <T>

import pe.edu.upc.mealscompose.data.model.SongResponse
import pe.edu.upc.mealscompose.data.model.SongsResponse
import retrofit2.Response //Esto es lo que sebe añadir para no romper
import retrofit2.http.GET
import retrofit2.http.Path

interface SongService {

    //Todo esto es perspectiva de la API

    @GET("api/songs")
    suspend fun getSongs(): Response<SongsResponse>

    // Opcional por si la pantalla de detalle necesita consultar una canción específica por su ID desde la API
    @GET("api/songs/{id}")
    suspend fun getSongById(@Path("id") id: Int): Response<SongResponse>

}