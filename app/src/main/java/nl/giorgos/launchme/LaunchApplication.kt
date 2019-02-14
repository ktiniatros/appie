package nl.giorgos.launchme

import android.app.Application
import nl.giorgos.launchme.inject.AppComponent
import nl.giorgos.launchme.inject.DaggerAppComponent
import nl.giorgos.launchme.inject.Modules

class LaunchApplication: Application() {
    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().modules(Modules(this)).build()
    }
}