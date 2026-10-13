package pe.edu.upc.mealscompose.presentation.di

import pe.edu.upc.mealscompose.data.di.DataModule

//import pe.edu.upc.mealscompose.presentation.viewmodel.CategoryListViewModel
import pe.edu.upc.mealscompose.presentation.viewmodel.MealListViewModel
import pe.edu.upc.mealscompose.presentation.viewmodel.SongListViewModel

object PresentationModule {
    /*
    fun getCategoryListViewModel(): CategoryListViewModel {
        return CategoryListViewModel(DataModule.getCategoryRepository())
    }

    fun getMealListViewModel(): MealListViewModel {
        return MealListViewModel(DataModule.getMealRepository())
    }
    */
    // Fábrica para proveer el ViewModel de canciones usando el repositorio del DataModule
    fun getSongListViewModel(): SongListViewModel {
        return SongListViewModel(DataModule.getSongRepository())
    }
}