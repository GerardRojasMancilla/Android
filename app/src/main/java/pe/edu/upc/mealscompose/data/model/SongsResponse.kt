package pe.edu.upc.mealscompose.data.model

import com.google.gson.annotations.SerializedName

data class SongsResponse(
    //Aqui se toma el listado completo de cada SongResponse
    @SerializedName("count")
    val count: Int?,
    @SerializedName("results")
    val results: List<SongResponse>
)
