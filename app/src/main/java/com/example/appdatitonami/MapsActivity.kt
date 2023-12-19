package com.example.appdatitonami

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.appdatitonami.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.CameraPosition

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in nina1410 and move the camera

        val nina1410 = LatLng(-33.51111, -70.77086)
        val santiagoMaipu = LatLng(-33.50984,-70.75676)

        mMap.addMarker(MarkerOptions().position(nina1410).title("Nina1410"))

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(nina1410,15f))
        //antes esto lo tenia comentado para que funcionara porque se cerraba el emulador de golpe.
        //si no funciona, por favor, comentar de aquí para abajo y funcionara. Gracias.
        val cameraPosition = CameraPosition.Builder()
           .target(nina1410) // Establece el centro del mapa en nina1410
            .zoom(17f) // Establece el zoom
            .bearing(90f) // Establece la orientación de la cámara al este
            .tilt(30f) // Establece la inclinación de la cámara a 30 grados
            .build() // Crea una CameraPosition a partir del constructor
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }
}