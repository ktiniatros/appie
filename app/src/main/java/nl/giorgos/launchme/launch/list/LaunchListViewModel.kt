package nl.giorgos.launchme.launch.list

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import nl.giorgos.launchme.LaunchApplication
import nl.giorgos.launchme.launch.api.Api
import nl.giorgos.launchme.launch.api.Launch
import retrofit2.Retrofit
import javax.inject.Inject

interface ItemClickListener {
    fun clickedItemOnPosition(position: Int)
}

class LaunchListViewModel(application: Application) :
    AndroidViewModel(application),
    ItemClickListener {

    @Inject
    lateinit var retrofit: Retrofit

    private val api: Api

    var items = MutableLiveData<List<Launch>>()

    init {
        getApplication<LaunchApplication>().appComponent.inject(this)
        api = retrofit.create(Api::class.java)
    }

    fun fetch(offset: Int = 0): List<Launch> {
        return api.getLaunches().execute().body()?.launches ?: ArrayList()
    }

    override fun clickedItemOnPosition(position: Int) {
        println("got you: $position")
        getApplication<LaunchApplication>().startActivity(Intent())
    }

    fun delegateObserver(observer: LifecycleOwner) {
        items.observe(observer, Observer { items ->
            print("GIO found ${items.size} items")
//            adapter.setItems(items)
        })
    }
}