package pe.edu.upc.mealscompose.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pe.edu.upc.mealscompose.data.model.CategoryResponse
import pe.edu.upc.mealscompose.data.remote.CategoryService

class CategoryRepository(val categoryService: CategoryService) {

    suspend fun getCategories(): List<CategoryResponse> = withContext(Dispatchers.IO) {

        val response = categoryService.getCategories()

        if (response.isSuccessful) {
            response.body()?.let {
                return@withContext(it.categories)
            }
        }
        return@withContext emptyList()
    }
}