package nl.giorgos.launchme.launch.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_detail.mapView
import nl.giorgos.launchme.R

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var viewModel: DetailViewModel

    private var lat: Double = 0.toDouble()
    private var long: Double = 0.toDouble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        viewModel.getLaunchWithId(intent.getIntExtra(IdExtraKey, InvalidId))?.let {
            setTitle(it.name)
            lat = it.lat.toDouble()
            long = it.long.toDouble()
            println("goooo map")
            mapView.getMapAsync(this)
        } ?: run {
            Toast.makeText(this, getString(R.string.invalid_launch), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        println("gooooing map")
        map?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(lat, long)))
    }

    companion object {
        val InvalidId = -1
        val IdExtraKey = "LAUNCH_ID_KEY"

        fun launchIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(IdExtraKey, id)
            return intent
        }
    }
}
