package com.oms.fashionhub

// ProfileImageDialog.kt
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import com.bumptech.glide.Glide

class ProfileImageDialog(context: Context, private val imageUrl: String) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_profile_image)

        val enlargedImageView: ImageView = findViewById(R.id.enlargedImageView)

        // Load the profile image using Glide (or your preferred image loading library)
        Glide.with(context).load(imageUrl).into(enlargedImageView)

        enlargedImageView.setOnClickListener {
            dismiss()
    }


    }
}
