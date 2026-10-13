package pe.edu.upc.mealscompose.domain

data class Song(
    val id: Int,
    val title : String,
    val artist: String,
    val album: String,
    val year: Int,
    val duration: Int, // Coincide con el tipo numérico del Swagger
    val genre: String,
    val rating: Double,
    val image: String, // Corregido según el JSON del Swagger ("image")
    var isFavorite: Boolean, // Para visualizar favortio

)
