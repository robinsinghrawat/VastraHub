package com.oms.fashionhub


import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation


class ZoomOutPageTransformer : Animation() {
    private val MIN_SCALE = 0.85f
    private val MIN_ALPHA = 0.5f

    val Any.view: View
        get() = this as View


    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        super.applyTransformation(interpolatedTime, t)

        val scale = 1f - MIN_SCALE * interpolatedTime
        val alpha = (255 * (1f - MIN_ALPHA * interpolatedTime)).toInt()

        t.matrix.apply {
            postScale(scale, scale)
            postTranslate(
                (t.view.width * (1 - scale) / 2),
                (t.view.height * (1 - scale) / 2)
            )
        }

        if (t.view is View) {
            (t.view as View).alpha = alpha.toFloat()
        }
    }
}
