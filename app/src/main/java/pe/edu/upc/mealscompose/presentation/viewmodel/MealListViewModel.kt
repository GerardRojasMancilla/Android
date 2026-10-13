package pe.edu.upc.mealscompose.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import pe.edu.upc.mealscompose.data.repository.MealRepository
import pe.edu.upc.mealscompose.domain.Meal

class MealListViewModel(val mealRepository: MealRepository) : ViewModel() {

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals

    fun getMealsByCategory(category: String) {
        viewModelScope.launch(Dispatchers.Main) {
            _meals.value = mealRepository.getMealsByCategory(category)
        }
    }

    fun insertMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.insertMeal(meal)
        }
    }

    fun deleteMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.deleteMeal(meal)
        }
    }
}