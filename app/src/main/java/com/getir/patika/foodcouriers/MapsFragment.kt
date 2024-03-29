package com.getir.patika.foodcouriers

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Locale

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var map: GoogleMap
    private var locationPermissionGranted: Boolean = false
    private lateinit var locationTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationTextView = view.findViewById<TextView>(R.id.adressText)

        // Initialize fused location client
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        // Check location permission
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
            // Request location updates
            getMapLocation()
        } else {
            // Request permission if not granted
            requestLocationPermission()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        if (locationPermissionGranted) {
            getMapLocation()
        }
    }

    private fun getMapLocation() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        // Check location permission again
        if (locationPermissionGranted) {
            try {
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        val currentLatLng = LatLng(location.latitude, location.longitude)
                        map.addMarker(
                            MarkerOptions().position(currentLatLng).title("Current Location")
                        )
                        map.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(currentLatLng, DEFAULT_ZOOM)
                        )

                        // Mevcut konumu yazdır
                        val geocoder = Geocoder(requireContext(), Locale.getDefault())
                        val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        val address = addresses?.getOrNull(0)?.getAddressLine(0)
                        if (address != null) {
                            locationTextView.text = "Current Address: $address"
                            locationTextView.visibility = View.VISIBLE
                        } else {
                            // Handle case where address is null or empty
                            // You can show a message to the user or take other action
                        }
                    }
                }
            } catch (securityException: SecurityException) {
                securityException.printStackTrace()
            }
        } else {
            // Handle case where permission is not granted
            // You can show a message to the user or take other action
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true
                getMapLocation()
            } else {
                // Handle case where permission is denied
                // You can show a message to the user or take other action
            }
        }
    }

    companion object {
        private const val DEFAULT_ZOOM = 15f
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }
}
