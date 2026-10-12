**data (La Capa de Datos)**

conseguir la información y dársela al resto de la aplicación

contiene: 

* conexiones a la API usando Retrofit (los servicios web y peticiones HTTP).

* modelos de datos de la red o base de datos local (como las clases que mapean el JSON).

* La implementación de los repositorios (el código que dice cómo se van a obtener los datos).


 **domain (La Capa de Dominio)**

 No sabe absolutamente nada de Android, ni de Retrofit, ni de bases de datos. Solo contiene lógica de negocio pura en código
 Kotlin estándar.

 contiene: 
 * Models: Las entidades o clases limpias que usa la lógica interna.,
 *
 * Repositories (Interfaces): Contratos que especifican qué datos se necesitan, pero sin programar cómo se obtienen.
 * Use Cases (Casos de uso): Clases que ejecutan una sola acción específica de la app

**presentation (La Capa de Presentación)**

contiene:
* ViewModels: Clases que guardan el estado de la pantalla, se comunican con los casos de uso del Domain y preparan
los datos para que la interfaz los muestre.

* Screens / Views: Tus archivos y componentes gráficos creados con Jetpack Compose

**ui.theme**

Esta carpeta viene precreada por Android Studio dentro de la capa de presentación.

¿Qué contiene?: Los estilos visuales de tu aplicación en Jetpack Compose, como los colores 
(Color.kt), las tipografías (Type.kt) y el tema general (Theme.kt) para mantener un diseño coherente (Material Design).


**Development**
Paso 1: La Capa domain -> Paso 2: La Capa data -> Paso 3: La Capa presentation -> Paso 4: La Capa de Interfaz / Pantallas

**Domain**

Núcleo de tu app y no debe depender de nada externo.Estas data class definen la estructura exacta de la información con la que va a trabajar la lógica interna de tu aplicación

**DATA: model->remote->local->repository->di**

remote (Remoto / Red): 

* consumo de APIs
* La interfaz del Servicio Web (WebService con las anotaciones de Retrofit como @GET) y el objeto cliente que instancia Retrofit con Gson.

local (Local / Base de datos)

* Aquí va todo lo relacionado con Room si tu aplicación guarda información dentro del celular.
* Las Entidades con la anotación @Entity, los DAO (@Dao) con las consultas SQL (@Query, @Insert, etc.) y la clase abstracta de la base de datos (RoomDatabase).

model (Modelos DTO):

* clases de datos que mapean exactamente la respuesta cruda que viene del servidor (JSON),separándolas de los modelos limpios del domain. (Aquí es donde usas el JSON to Kotlin).

* NameResponse: Al consumir servicios web con Retrofit (ideal para generar automáticamente con plugins como **JSON to Kotlin**)
* NameEntity: Al almacenar información de manera persistente en el dispositivo con anotaciones como @Entity
* NameMapper: Para mantener desacoplada la capa de red/base de datos de la lógica de negocio pura.

* La anotación @SerializedName actúa como un puente o traductor: le dice a Gson: "Cuando leas el campo idCategory del JSON, guárdalo dentro de la variable id de Kotlin".


repository (Implementación del Repositorio):

* Aquí creas la clase que implementa la interfaz que dejaste en el domain (por ejemplo, MealRepositoryImpl).
* Esta clase actúa como un "puente inteligente": decide si va a buscar los datos a internet usando el remote (Retrofit) o a la memoria interna usando el local (Room).
 
di:

* sirve para centralizar la creación de instancias (como el cliente de Retrofit o la base de datos de Room) para que se compartan en toda la aplicación.

**PRESENTATION**




