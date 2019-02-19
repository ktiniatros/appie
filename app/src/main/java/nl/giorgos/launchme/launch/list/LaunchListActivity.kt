package nl.giorgos.launchme.launch.list

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.activity_main.loadingIndicator
import kotlinx.android.synthetic.main.activity_main.recyclerView
import nl.giorgos.launchme.LaunchApplication
import nl.giorgos.launchme.R
import nl.giorgos.launchme.launch.detail.DetailActivity

class LaunchListActivity : AppCompatActivity(),
    ItemClickListener,
    SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: LaunchListViewModel

    private val adapter: LaunchAdapter = LaunchAdapter(this)

    private lateinit var searchView: SearchView

    override fun clickedItemOnPosition(position: Int) {
        viewModel.items.value?.get(position)?.let { launch ->
            val launchIntent = DetailActivity.launchIntent(this, launch.id)
            startActivity(launchIntent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search, menu)

        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(viewModel)

        return true
    }

    override fun onRefresh() {
        loadingIndicator.isRefreshing = true
        viewModel.fetchItems()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        (application as LaunchApplication).appComponent.inject(this)

        setupLoadingIndicator()

        setupList()

        setupViewModel()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(LaunchListViewModel::class.java)

        viewModel.items.observe(this, Observer { items ->
            adapter.setItems(items)
            loadingIndicator.isRefreshing = false
        })

        viewModel.fetchItems()
    }

    private fun setupLoadingIndicator() {
        loadingIndicator.isRefreshing = true
        loadingIndicator.setOnRefreshListener(this)
    }

    private fun setupList() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))
    }
}
