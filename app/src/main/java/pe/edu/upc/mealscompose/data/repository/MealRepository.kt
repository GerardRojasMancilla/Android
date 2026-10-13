package pe.edu.upc.mealscompose.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.mealscompose.data.local.MealDao
import pe.edu.upc.mealscompose.data.model.MealEntity
import pe.edu.upc.mealscompose.data.model.MealMapper
import pe.edu.upc.mealscompose.data.remote.MealService
import pe.edu.upc.mealscompose.domain.Meal

class MealRepository(
    val mealService: MealService,
    val mealDao: MealDao
) {

    suspend fun insertMeal(meal: Meal) = withContext(Dispatchers.IO) {
        mealDao.insertMeal(MealEntity(id = meal.id, name = meal.name))
    }

    suspend fun deleteMeal(meal: Meal) = withContext(Dispatchers.IO) {
        mealDao.deleteMeal(MealEntity(id = meal.id, name = meal.name))
    }

    suspend fun getMealsByCategory(category: String): List<Meal> =
        withContext(Dispatchers.IO) {
            val response = mealService.getMealsByCategory(category)
            if (response.isSuccessful) {
                response.body()?.let { it ->
                    return@withContext it.meals.map { mealResponse ->
                       val meal = MealMapper.toMeal(mealResponse)
                        meal.copy(
                            isFavorite = mealDao.fetchMealById(meal.id).isNotEmpty()
                        )
                    }
                }
            }

            return@withContext emptyList()
        }
}