package nl.giorgos.launchme

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.recyclerView
import nl.giorgos.launchme.launch.list.LaunchAdapter
import nl.giorgos.launchme.launch.list.LaunchListViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: LaunchListViewModel

    private lateinit var adapter: LaunchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(LaunchListViewModel::class.java)
        adapter = LaunchAdapter(viewModel)

        setupUI()

        viewModel.items.observe(this, Observer { items ->
            adapter.setItems(items)
        })

        println("GIO ACT onCreate")
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
        println("GIO ACT onDestroy")
    }
}
