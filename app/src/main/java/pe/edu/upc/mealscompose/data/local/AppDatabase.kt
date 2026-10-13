package pe.edu.upc.mealscompose.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.edu.upc.mealscompose.data.model.MealEntity
import pe.edu.upc.mealscompose.data.model.SongEntity

//@Database(entities = [SongEntity::class], version = 1): Le indica a Room qué
// tablas debe crear y administrar (en este caso, mapea la entidad SongEntity)
// y define la versión actual del esquema de la base de datos.
@Database(entities = [MealEntity::class, SongEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun mealDao(): MealDao

    //abstract fun songDao(): SongDao: Expone el DAO para que
    // el Repositorio pueda utilizar sus métodos de lectura y escritura.
    abstract fun songDao(): SongDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "song_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

    /*
    * @Volatile private var INSTANCE: AppDatabase? = null: Declara una variable global que almacenará la única instancia de la base de datos. La palabra clave @Volatile asegura que el valor de esta variable esté siempre actualizado y sea visible de manera inmediata para todos los hilos de ejecución de la aplicación.

    fun getDatabase(context: Context): AppDatabase: Es la función pública que llamaremos desde el exterior cuando necesitemos acceder a la base de datos pasándole el contexto de la aplicación.

    return INSTANCE ?: synchronized(this) { ... }: Evalúa si la instancia ya existe (INSTANCE). Si ya fue creada anteriormente, la retorna directamente de inmediato. Si es nula (primera vez que se llama), entra al bloque synchronized(this). Esto último es un cerrojo de seguridad para que, si dos hilos intentan crear la base de datos exactamente al mismo tiempo, uno espere a que el otro termine y no se creen dos bases de datos paralelas por error.

    Room.databaseBuilder(...): Es el constructor oficial de Android Room. Aquí es donde se le indica:

    El contexto de la aplicación (context.applicationContext).

    La clase que define la estructura (AppDatabase::class.java).

    El nombre físico que tendrá el archivo de la base de datos en el almacenamiento interno del celular ("song_db").

    .build(): Finalmente, construye y compila la base de datos en el dispositivo, guardándola en la variable INSTANCE para que las próximas llamadas la reutilicen al instante.
    *
* */

}