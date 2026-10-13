package pe.edu.upc.mealscompose.data.di

import androidx.room.Room
import pe.edu.upc.mealscompose.MealsApplication
import pe.edu.upc.mealscompose.SongsApplication
import pe.edu.upc.mealscompose.data.local.AppDatabase
import pe.edu.upc.mealscompose.data.local.MealDao
import pe.edu.upc.mealscompose.data.local.SongDao
import pe.edu.upc.mealscompose.data.remote.ApiConstants.BASE_URL
import pe.edu.upc.mealscompose.data.remote.CategoryService
import pe.edu.upc.mealscompose.data.remote.MealService
import pe.edu.upc.mealscompose.data.remote.SongService
import pe.edu.upc.mealscompose.data.repository.CategoryRepository
import pe.edu.upc.mealscompose.data.repository.MealRepository
import pe.edu.upc.mealscompose.data.repository.SongRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    // 1. Instancia global de Retrofit apuntando a la URL base de Azure
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 2. Creación del servicio web para consumir las canciones
    fun getSongService(): SongService {
        return getRetrofit().create(SongService::class.java)
    }

    // 3. Creación de la base de datos Room usando el contexto de la aplicación
    fun getAppDatabase(): AppDatabase {
        return Room.databaseBuilder(
            //Aqui lo correcto seria SongApplication, ahora si lo puedo colocar
            //pq ya he creado esa refenrecia afuea del mainactivity
            MealsApplication.instance.applicationContext,
            AppDatabase::class.java,
            "song_db"
        ).build()
    }
    // 4. Extracción del DAO a partir de la base de datos
    fun getSongDao(): SongDao {
        return getAppDatabase().songDao()
    }

    // 5. Inyección del servicio y el DAO dentro del Repositorio
    fun getSongRepository(): SongRepository {
        return SongRepository(getSongService(), getSongDao())
    }

    //-------OTHER--------------
    /*
    fun getCategoryService(): CategoryService {
        return getRetrofit().create(CategoryService::class.java)
    }

    fun getCategoryRepository(): CategoryRepository {
        return CategoryRepository(getCategoryService())
    }

    fun getMealService(): MealService {
        return getRetrofit().create(MealService::class.java)
    }

    fun getMealRepository(): MealRepository {
        return MealRepository(getMealService(), getDao())
    }

    fun getDao(): MealDao {
        return getAppDatabase().mealDao()
    }
    */
    /*
    fun getAppDatabase(): AppDatabase {
        return Room.databaseBuilder(
            MealsApplication.instance.applicationContext, AppDatabase::class.java, "meas-db"
        ).build()
    }
    */

}