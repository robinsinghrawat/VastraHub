package com.oms.fashionhub


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class Onboardingscreen : AppCompatActivity() {
    private lateinit var indicatorLayout: TabLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboardingscreen)



        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val adapter = OnboardingPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter



        // Optionally customize the indicators

    }

}