package nl.giorgos.launchme.launch.list

import android.app.Application
import android.content.Intent
import android.widget.SearchView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import nl.giorgos.launchme.LaunchApplication
import nl.giorgos.launchme.launch.api.Launch
import nl.giorgos.launchme.launch.api.LaunchRepository
import nl.giorgos.launchme.launch.detail.DetailActivity
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

interface ItemClickListener {
    fun clickedItemOnPosition(position: Int)
}

class LaunchListViewModel(application: Application) :
    AndroidViewModel(application),
    SearchView.OnQueryTextListener {

    // default setup talks via Main Dispatcher(UI Thread)
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    @Inject
    lateinit var launchRepository: LaunchRepository

    var items = MutableLiveData<List<Launch>>()

    init {
        getApplication<LaunchApplication>().appComponent.inject(this)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let { text ->
            items.value = if (text.isEmpty())
                launchRepository.currentLaunches
            else
                launchRepository.getLaunchesByMissionName(text)
        }
        return true
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }

    fun fetchItems() = scope.launch(Dispatchers.IO) {
        onItemsFetched(launchRepository.fetch())
    }

    private fun onItemsFetched(newItems: List<Launch>) = scope.launch {
        items.value = newItems
    }
}