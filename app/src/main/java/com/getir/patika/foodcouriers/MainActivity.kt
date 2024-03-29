package com.getir.patika.foodcouriers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_maps)

        val fragment = MapsFragment()
        supportFragmentManager.beginTransaction().replace(R.id.map, fragment).commit()
        if(!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyCaQfgz_dbGg5GpbVNCEHWy6G9ftWQLjpw")
            val autocompleteSupportFragment =
                (supportFragmentManager.findFragmentById(R.id.place_autocomplete)
                        as AutocompleteSupportFragment)
            autocompleteSupportFragment.setPlaceFields(
                listOf(Place.Field.LAT_LNG, Place.Field.NAME))
            autocompleteSupportFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener{
                override fun onError(p0: Status) {
                    Toast.makeText(this@MainActivity, "Some Error in Search", Toast.LENGTH_SHORT).show()
                }

                override fun onPlaceSelected(place: Place) {
                    val latLng = place.latLng
                    if (latLng != null) {
                        val mapsFragment =
                            supportFragmentManager.findFragmentById(R.id.map) as MapsFragment
                        mapsFragment.moveCameraToLocation(latLng)

                        // Adres bilgisini al ve ekranda göster
                        val address = place.name
                        Toast.makeText(
                            this@MainActivity,
                            "Selected Address: $address",
                            Toast.LENGTH_SHORT
                        ).show()
                    }}

            })


        }
    }
}