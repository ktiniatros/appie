package nl.giorgos.launchme.launch.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import nl.giorgos.launchme.LaunchApplication
import nl.giorgos.launchme.launch.api.Launch
import nl.giorgos.launchme.launch.api.LaunchRepository
import java.util.Date
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

data class ViewData(val name: String, val lat: Float, val long: Float)

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var launchRepository: LaunchRepository

    val coroutineUiScope = CoroutineScope(Dispatchers.Main)

    val timerData = MutableLiveData<String>()

    var isResumed = false

    lateinit var launchDate: Date

    init {
        getApplication<LaunchApplication>().appComponent.inject(this)
    }

    fun getLaunchWithId(id: Int): ViewData? {
        launchRepository.findLaunchWithId(id)?.let { launch ->
            launchDate = launch.isoend
            startTimer()
            return ViewData(
                launch.name,
                launch.location.pads[0].latitude,
                launch.location.pads[0].longitude)
        }
        return null
    }

    fun startTimer() {
        val launchDateWrap = launchDate as? Date
        launchDateWrap?.let { date ->
            Timer().schedule(object : TimerTask() {
                override fun run() {
                    coroutineUiScope.launch {
                        timerData.value = Launch.getCountDownText(Date(), date)
                    }
                    if (isResumed) {
                        startTimer()
                    }
                }
            }, 1000)
        }
    }
}