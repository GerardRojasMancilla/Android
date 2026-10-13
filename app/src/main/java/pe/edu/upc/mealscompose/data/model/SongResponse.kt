package pe.edu.upc.mealscompose.data.model

import com.google.gson.annotations.SerializedName

//Traduccion de los datos Json a lo que es Kotlin
data class SongResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("title")
    val title : String,
    @SerializedName("artist")
    val artist: String?,
    @SerializedName("album")
    val album: String?,
    @SerializedName("year")
    val year: Int?,
    @SerializedName("duration")
    val duration: Int?,
    @SerializedName("genre")
    val genre: String?,
    @SerializedName("rating")
    val rating: Double?,
    @SerializedName("image")
    val image: String?
)
