package nl.giorgos.launchme.launch.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import nl.giorgos.launchme.LaunchApplication
import nl.giorgos.launchme.launch.api.LaunchRepository
import javax.inject.Inject

data class ViewData(val name: String, val lat: Float, val long: Float)

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var launchRepository: LaunchRepository

    init {
        getApplication<LaunchApplication>().appComponent.inject(this)
    }

    fun getLaunchWithId(id: Int): ViewData? {
        launchRepository.findLaunchWithId(id)?.let { launch ->
            return ViewData(
                launch.name,
                launch.location.pads[0].latitude,
                launch.location.pads[0].longitude
            )
        }
        return null
    }
}