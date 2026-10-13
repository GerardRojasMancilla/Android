package pe.edu.upc.mealscompose.domain

data class Meal(
    val id: String,
    val name: String,
    val poster: String,
    var isFavorite: Boolean = false
)
