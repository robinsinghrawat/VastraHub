package com.oms.fashionhub

import android.view.View
import androidx.viewpager.widget.ViewPager

class DepthPageTransformer : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val pageWidth = page.width

        if (position < -1) {
            // This page is way off-screen to the left.
            page.alpha = 0f

        } else if (position <= 0) {
            // Use the default slide transition when moving to the left.
            page.alpha = 1f
            page.translationX = 0f
            page.scaleX = 1f
            page.scaleY = 1f

        } else if (position <= 1) {
            // Fade the page out.
            page.alpha = 1 - position

            // Counteract the default slide transition
            page.translationX = pageWidth * -position

            // Scale the page down (between MIN_SCALE and 1)
            val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
            page.scaleX = scaleFactor
            page.scaleY = scaleFactor

        } else {
            // This page is way off-screen to the right.
            page.alpha = 0f
        }
    }

    companion object {
        private const val MIN_SCALE = 0.75f
    }
}
