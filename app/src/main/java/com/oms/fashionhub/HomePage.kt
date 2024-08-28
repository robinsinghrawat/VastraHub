package com.oms.fashionhub

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {

    private val homeScrreen= HomeScrreen()
    private val cart = Cart()
    private val search= Search()
    private val profilework =Profilework()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(homeScrreen)
                R.id.like -> replaceFragment(cart)
                R.id.search -> replaceFragment(search)
                R.id.profile -> replaceFragment(profilework)
            }
            true
        }

        // Set the initial selected item
        bottomNavigationView.selectedItemId = R.id.home
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.mainContent, fragment)
            .commit()
    }
}
