package com.oms.fashionhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ChildFragmernt : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_child_fragmernt, container, false)
        val gifImageView: ImageView = view.findViewById(R.id.kidgif)

        Glide.with(this)
            .asGif()
            .load(R.drawable.kids) // Replace with the actual GIF resource ID
            .placeholder(R.drawable.ic_launcher_foreground) // Replace with a placeholder image resource ID
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(gifImageView)

        return view
    }

}