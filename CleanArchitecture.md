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
