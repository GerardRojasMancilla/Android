# **data (La Capa de Datos)**

conseguir la información y dársela al resto de la aplicación

contiene: 

* conexiones a la API usando Retrofit (los servicios web y peticiones HTTP).

* modelos de datos de la red o base de datos local (como las clases que mapean el JSON).

* La implementación de los repositorios (el código que dice cómo se van a obtener los datos).


 # **domain (La Capa de Dominio)**

 No sabe absolutamente nada de Android, ni de Retrofit, ni de bases de datos. Solo contiene lógica de negocio pura en código
 Kotlin estándar.

 contiene: 
 * Models: Las entidades o clases limpias que usa la lógica interna.,
 *
 * Repositories (Interfaces): Contratos que especifican qué datos se necesitan, pero sin programar cómo se obtienen.
 * Use Cases (Casos de uso): Clases que ejecutan una sola acción específica de la app

# **presentation (La Capa de Presentación)**

contiene:
* ViewModels: Clases que guardan el estado de la pantalla, se comunican con los casos de uso del Domain y preparan
los datos para que la interfaz los muestre.

* Screens / Views: Tus archivos y componentes gráficos creados con Jetpack Compose

# **ui.theme**

Esta carpeta viene precreada por Android Studio dentro de la capa de presentación.

¿Qué contiene?: Los estilos visuales de tu aplicación en Jetpack Compose, como los colores 
(Color.kt), las tipografías (Type.kt) y el tema general (Theme.kt) para mantener un diseño coherente (Material Design).


# **Development**
Paso 1: La Capa domain -> Paso 2: La Capa data -> Paso 3: La Capa presentation -> Paso 4: La Capa de Interfaz / Pantallas

## **Domain**

Núcleo de tu app y no debe depender de nada externo.Estas data class definen la estructura exacta de la información con la que va a trabajar la lógica interna de tu aplicación

## **DATA: model->remote->local->repository->di**

**remote (Remoto / Red):** 

* consumo de APIs
* La interfaz del Servicio Web (WebService con las anotaciones de Retrofit como @GET) y el objeto cliente que instancia Retrofit con Gson.

- ApiConstants.kt: Almacena la URL base o raíz del servidor web al que tu aplicación va a realizar las peticiones
- Interfaces WebService (CategoryService, MealService): Define los endpoints (rutas específicas) y los métodos HTTP (como @GET o @POST) que utilizará Retrofit.
-@Query("c") category: String: Es la clave de esta función. Permite enviar un parámetro dinámico en la URL de la petición.

**local (Local / Base de datos)** 

* Aquí va todo lo relacionado con Room si tu aplicación guarda información dentro del celular.
* Las Entidades con la anotación @Entity, los DAO (@Dao) con las consultas SQL (@Query, @Insert, etc.) y la clase abstracta de la base de datos (RoomDatabase).

* AppDatabase.kt: clase abstracta central que actúa como el punto de acceso principal a la base de datos SQLite del dispositivo.
* abstract fun mealDao(): MealDao Expone el DAO para que el resto de la aplicación pueda invocar las consultas y operaciones SQL.

* MealDao.kt: Define las operaciones concretas que puedes hacer sobre la tabla de la base de datos local.

**model (Modelos DTO):** 

* clases de datos que mapean exactamente la respuesta cruda que viene del servidor (JSON),separándolas de los modelos limpios del domain. (Aquí es donde usas el JSON to Kotlin).

* NameResponse: Al consumir servicios web con Retrofit (ideal para generar automáticamente con plugins como **JSON to Kotlin**)
* NameEntity: Al almacenar información de manera persistente en el dispositivo con anotaciones como @Entity
* NameMapper: Para mantener desacoplada la capa de red/base de datos de la lógica de negocio pura, sirve como un puente traductor para transformar objetos de la capa externa (como la respuesta de la API MealResponse) en el modelo puro y limpio que exige tu negocio (Meal).

-object MealMapper: En Kotlin, usar un object crea un Singleton (un objeto único que no necesita ser instanciado con val mapper = MealMapper(), sino que lo llamas directamente como MealMapper.toMeal(...)).

-toMeal(...): Toma los datos que vinieron de internet (que pueden venir nulos o con nombres extraños y seguros con el operador ?: "") y los convierte al modelo limpio de la capa domain.

 

* La anotación @SerializedName actúa como un puente o traductor: le dice a Gson: "Cuando leas el campo idCategory del JSON, guárdalo dentro de la variable id de Kotlin".


**repository (Implementación del Repositorio):** 

* Aquí creas la clase que implementa la interfaz que dejaste en el domain (por ejemplo, MealRepositoryImpl).
* Esta clase actúa como un "puente inteligente": decide si va a buscar los datos a internet usando el remote (Retrofit) o a la memoria interna usando el local (Room).

-CategoryRepository.kt (Solo Red / API): 

 withContext(Dispatchers.IO): Es una buena práctica obligatoria en Android.

 Validación de éxito (isSuccessful): Revisa si el servidor respondió con un código HTTP exitoso (como el rango 200). Si es así, extrae el cuerpo (response.body()); de lo contrario, devuelve una lista vacía para evitar que la aplicación falle.

 -MealRepository.kt (Red + Base de Datos Local + Mappers)
 Inyección de dependencias: Recibe tanto el servicio de red (MealService) como el acceso a datos local (MealDao) en su constructor.

 Uso del Mapper y Dominio: Transforma el objeto que viene de la red (mealResponse) al modelo puro del dominio (Meal) utilizando el MealMapper.

 Lógica inteligente (meal.copy(...)): No solo descarga los datos de la API, sino que consulta a Room (mealDao.fetchMealById) para verificar de manera dinámica si ese plato ya fue guardado previamente como favorito en el teléfono.

 

**di:** 

* sirve para centralizar la creación de instancias (como el cliente de Retrofit o la base de datos de Room) para que se compartan en toda la aplicación.

* Configuración de Retrofit: Crea la instancia única y principal de Retrofit, indicándole cuál es la URL base (BASE_URL) y configurando el conversor de Gson para transformar los JSONs en objetos de Kotlin.

* Creación de los Servicios Web: Utiliza la instancia de Retrofit para generar la implementación concreta de tus interfaces de red (CategoryService y MealService).

* Creación de la Base de Datos Local (Room): Construye y levanta la base de datos local SQLite en el dispositivo utilizando el contexto global de la aplicación (MealsApplication.instance) y asignándole un nombre al archivo ("meas-db").

* Provisión de los Repositorios: Es el punto clave de la arquitectura. Toma automáticamente el servicio de red (getMealService()) y el acceso a datos local (getDao()) para inyectarlos dentro del constructor de MealRepository, entregándote el repositorio listo para ser usado por el ViewModel.

## **PRESENTATION(viewmodel->view->navigation->di)**

**viewmodel**
* Es el que se comunica directamente con los repositorios de la capa data o domain para pedir los datos, procesarlos y exponerlos como estados observables (manejando corrutinas y listas).

* ¿Por qué se usa doble variable (_categories y categories)? Es una buena práctica de encapsulamiento (patrón de estado privado mutable y público de solo lectura):

_categories (Privada y Mutable): Solo el ViewModel puede modificarla (escribir nuevos datos o limpiar la lista).

categories (Pública e Inmutable): Es la que observa la vista (la pantalla). La pantalla puede leer los datos y redibujarse automáticamente cuando cambian, pero no puede alterarlos directamente por seguridad.

*viewModelScope lanza una corrutina atada al ciclo de vida del ViewModel.

Ventaja clave: Si el usuario sale de la pantalla (y el ViewModel se destruye), todas las peticiones de red o procesos en segundo plano se cancelan automáticamente, evitando fugas de memoria (memory leaks) y consumo innecesario de batería.



**view**
* Contiene los componentes visuales en Jetpack Compose (las pantallas, tarjetas, listas LazyColumn, etc.). Consumen directamente el ViewModel para pintar los datos en la interfaz y capturar las interacciones del usuario.

* CategoryListView y el uso de Coil (AsyncImage): Utiliza la librería Coil (AsyncImage)
-MealListview y la Gestión de Favoritos (toggleFavorite): Escucha cuando el usuario presiona el botón de favorito (el ícono del corazón con IconButton). Si el estado pasa a favorito (isFavorite = true), llama al ViewModel para insertar el plato en la base de datos local (Room); de lo contrario, ejecuta la función para eliminarlo.
  
**navigation**
* Una vez que ya tienes diseñadas tus vistas individuales (view), creas las rutas y el grafo de navegación para conectar una pantalla con otra (por ejemplo, pasar de la lista de platos al detalle).

-onTap: (String?) -> Unit = {}: Es una función de orden superior (callback) que se ejecuta cuando el usuario hace clic en una tarjeta (onClick = { onTap(category.name) }).

- rememberNavController(): Crea y recuerda el controlador principal de navegación, que es el encargado de saber en qué pantalla está el usuario y permitirle moverse de una ruta a otra (navController.navigate(...)).

- startDestination = "categories": Define cuál será la primera pantalla que verá el usuario al abrir la sección.

**di**

* Si tu ViewModel necesita recibir repositorios u otros casos de uso en su constructor, configuras aquí cómo se inyectan (o se instancian de forma limpia).

* Inyección manual centralizada: Al igual que lo hiciste en la capa data con el DataModule, este módulo actúa como un proveedor limpio para instanciar los ViewModels.

* Conexión automática: Cada vez que una vista (CategoryListView o MealListview) necesita su respectivo ViewModel, llama a este módulo. El módulo se encarga por detrás de pedirle el repositorio correspondiente al DataModule (DataModule.getCategoryRepository()) y se lo inyecta al constructor del ViewModel sin que la interfaz tenga que preocuparse por cómo se crearon las bases de datos o los servicios web.

# **Ejecutar la aplicación**

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MealsComposeTheme {
                Home() // ¡Aquí arranca la interfaz gráfica! y funciona solo llamando eso que fue definido en navigation
            }
        }
    }
}

¿Qué hace? Es la primera Activity (pantalla contenedora) que se ejecuta cuando el usuario hace clic en el ícono de tu aplicación en el celular.

¿Cómo inicia la app? Dentro del bloque setContent, envuelve todo con MealsComposeTheme (para aplicar los colores y estilos de la carpeta ui.theme) y luego llama directamente a Home(), que es la función de navegación principal que vimos antes (la que tiene el menú inferior y las categorías).


## **MealsApplication.kt (El punto de entrada global de sistema)**

class MealsApplication: Application() {

    companion object {
        lateinit var instance: MealsApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

¿Qué hace? Hereda de la clase Application de Android, lo que significa que es lo primerísimo que se ejecuta en toda la aplicación, incluso antes de que se abra cualquier pantalla (MainActivity).

¿Para qué sirve en tu proyecto? Implementa el patrón Singleton (lateinit var instance: MealsApplication). Recuerdas que en tu DataModule (en la capa de datos) tenías esto para crear la base de datos de Room?

