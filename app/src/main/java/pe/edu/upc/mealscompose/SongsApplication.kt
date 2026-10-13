package pe.edu.upc.mealscompose

import android.app.Application

class SongsApplication:  Application() {
    companion object {
        lateinit var instance: SongsApplication

    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}