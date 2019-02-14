package nl.giorgos.launchme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.recyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import nl.giorgos.launchme.launch.list.LaunchAdapter
import nl.giorgos.launchme.launch.list.LaunchListViewModel
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {
    // default setup talks via Main Dispatcher(UI Thread)
    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    private lateinit var viewModel: LaunchListViewModel

    private lateinit var adapter: LaunchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(LaunchListViewModel::class.java)
        adapter = LaunchAdapter(viewModel)

        setupUI()

        viewModel.delegateObserver(this)

        scope.launch(Dispatchers.IO) {
            val newItems = viewModel.fetch()
            scope.launch {
                viewModel.items.value = newItems
            }
        }
    }

    private fun setupUI() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
    }

    override fun onDestroy() {
        super.onDestroy()
        parentJob.cancel()
    }
}
