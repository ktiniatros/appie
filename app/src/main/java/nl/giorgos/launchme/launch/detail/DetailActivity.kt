package nl.giorgos.launchme.launch.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator
import kotlinx.android.synthetic.main.activity_detail.mapView
import kotlinx.android.synthetic.main.activity_detail.timerTextView
import nl.giorgos.launchme.R

class DetailActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var viewModel: DetailViewModel

    private var lat: Double = 0.toDouble()
    private var long: Double = 0.toDouble()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        mapView.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)

        viewModel.timerData.observe(this, Observer { timerText ->
            timerTextView.text = timerText
        })

        viewModel.getLaunchWithId(intent.getIntExtra(IdExtraKey, InvalidId))?.let {
            setTitle(it.name)
            lat = it.lat.toDouble()
            long = it.long.toDouble()
            mapView.getMapAsync(this)
        } ?: run {
            Toast.makeText(this, getString(R.string.invalid_launch), Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
        viewModel.isResumed = true
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
        viewModel.isResumed = false
    }

    override fun onMapReady(map: GoogleMap?) {
        val iconGenerator = IconGenerator(this)
        iconGenerator.setStyle(R.style.map_marker_bubble)
        val iconFromText = iconGenerator.makeIcon(getString(R.string.map_marker_icon_text))
        val position = LatLng(lat, long)
        val markerOptions = MarkerOptions()
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(iconFromText))
        markerOptions.position(position)
        map?.addMarker(markerOptions)
        map?.moveCamera(CameraUpdateFactory.newLatLng(position))
        map?.uiSettings?.setAllGesturesEnabled(false)
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
