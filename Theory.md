**LIBRERIAS Concept**

1.Consumo de la API REST (Internet)
Retrofit: Conectar tu app con servidores web, Convierte las peticiones HTTP (como GET o POST) en llamadas a funciones de Kotlin

2. Carga de Imágenes desde URLs
Coil: Es la librería moderna en Kotlin para cargar imágenes desde internet en Jetpack Compose

3.Navegación y Pantallas
Navigation Compose: Permite gestionar la navegación entre diferentes pantallas (por ejemplo, pasar de una lista de elementos a una pantalla de detalle) dentro de una aplicación basada en Jetpack Compose.

5. Gestión de Estado y Arquitectura
ViewModel: Mantiene los datos de la pantalla seguros y vivos aunque el usuario gire el teléfono o la actividad se reinicie.
Room: Es la base de datos local de Android (sobre SQLite). Se usa si te piden guardar información de forma local en el dispositivo (modo offline)

**CONFIGURAR PERMISOS**
Se coloca dentro de AndroidManifest.xml
Permisos de internet: <uses-permission android:name="android.permission.INTERNET" />
debe ponerse antes de la etiqueta <application .....>

**CONFIGURAR LIBRERIAS**
[versions]
retrofit = "2.11.0" y coilCompose = "3.1.0" : manejar peticiones de red y la carga de imágenes
roomRuntime = "2.7.1" y ksp = "2.0.21-1.0.28": Controlan la base de datos local
navigationCompose = "2.8.9" y lifecycleViewmodelCompose = "2.8.7": Fundamentales para moverte entre pantallas y retener datos en tus ViewModels.

[libraries]
* Rest:
retrofit = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit"}
retrofit-converter-gson = { group = "com.squareup.retrofit2", name = "converter-gson", version.ref = "retrofit"}

* Coil:
coil-compose = { module = "io.coil-kt.coil3:coil-compose", version.ref = "coilCompose" }
coil-network-okhttp = { module = "io.coil-kt.coil3:coil-network-okhttp", version.ref = "coilCompose" }

* Navigation and ViewModels:
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
androidx-lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycleViewmodelCompose" }

* Local Data Base(Room)
androidx-room-runtime = { module = "androidx.room:room-runtime", version.ref = "roomRuntime" }
androidx-room-ktx = { module = "androidx.room:room-ktx", version.ref = "roomRuntime" }
androidx-room-compiler = { module = "androidx.room:room-compiler", version.ref = "roomRuntime" }

[plugins]

android-application = { id = "com.android.application", version.ref = "agp" }
kotlin-android = { id = "org.jetbrains.kotlin.android", version.ref = "kotlin" }
kotlin-compose = { id = "org.jetbrains.kotlin.plugin.compose", version.ref = "kotlin" }
ksp = { id = "com.google.devtools.ksp", version.ref="ksp"}

