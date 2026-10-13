package pe.edu.upc.mealscompose

import android.app.Application

class MealsApplication: Application() {

    companion object {
        lateinit var instance: MealsApplication

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}