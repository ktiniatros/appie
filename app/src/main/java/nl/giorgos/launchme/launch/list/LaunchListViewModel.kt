package nl.giorgos.launchme.launch.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import nl.giorgos.launchme.LaunchApplication
import nl.giorgos.launchme.launch.api.Api
import nl.giorgos.launchme.launch.api.Launch
import retrofit2.Retrofit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LaunchListViewModel(application: Application): AndroidViewModel(application) {
    @Inject
    lateinit var retrofit: Retrofit

    private val api: Api

    val items = ArrayList<Launch>()

    init {
        (application as LaunchApplication).appComponent.inject(this)
        api = retrofit.create(Api::class.java)
    }

    suspend fun fetch(offset: Int = 0) {
        println(api.getLaunches().execute())
    }
}