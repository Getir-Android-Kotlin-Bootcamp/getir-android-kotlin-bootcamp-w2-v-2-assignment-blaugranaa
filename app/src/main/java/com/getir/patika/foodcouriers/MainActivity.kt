package com.getir.patika.foodcouriers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_maps)

        val fragment = MapsFragment()
        supportFragmentManager.beginTransaction().replace(R.id.map, fragment).commit()
        if(!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyBEkN8FBe4Ji7iWQgJ4WDyTocgVVe0XgL8")
            val autocompleteSupportFragment =
                (supportFragmentManager.findFragmentById(R.id.place_autocomplete) as AutocompleteSupportFragment).setPlaceFields(
                    listOf(Place.Field.LAT_LNG, Place.Field.NAME)
                )
        }
    }
}