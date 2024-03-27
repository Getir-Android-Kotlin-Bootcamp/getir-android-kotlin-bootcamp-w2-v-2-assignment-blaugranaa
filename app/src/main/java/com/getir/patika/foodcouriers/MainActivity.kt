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

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_maps)
      /*  tabLayout = findViewById(R.id.tab_account)
        viewPager2 = findViewById(R.id.viewpager_account)
        pagerAdapter = PagerAdapter(supportFragmentManager,lifecycle).apply {
            addFragment(CreateAccountFragment())
            addFragment(LoginAccountFragment())
        }
        viewPager2.adapter = pagerAdapter

        TabLayoutMediator(tabLayout,viewPager2){ tab, position ->
             when(position) {
                 0 -> {
                     tab.text = "Create Account"
                 }
                 1 -> {
                     tab.text = "Login Account"
                 }
             }

        }.attach() */

        if(!Places.isInitialized()){
            Places.initialize(applicationContext, "AIzaSyBEkN8FBe4Ji7iWQgJ4WDyTocgVVe0XgL8")
            val autocompleteSupportFragment = (supportFragmentManager.findFragmentById(R.id.place_autocomplete) as AutocompleteSupportFragment).setPlaceFields(
                listOf(Place.Field.LAT_LNG, Place.Field.NAME)
            )

          //  autocompleteSupportFragment.setOnPlaceSelectedListener()

        }

    }


}