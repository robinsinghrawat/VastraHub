package com.oms.fashionhub

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.card.MaterialCardView


class onboardingscreen2 : Fragment() {

    lateinit var textView: TextView
    lateinit var button: MaterialCardView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_onboardingscreen2, container, false)
        textView=view.findViewById(R.id.text4)
        val text = "  <font color='#000000'>Whishlist: Where </font> <font color='#5B3822'>Fashion Dreams</font> <font color='#000000'>Begins</font>"

        textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        // Inflate the layout for this fragment


        button=view.findViewById(R.id.next2)
        button.setOnClickListener {
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(
                R.id.fragmentContainer, // Replace with the container ID of your fragments
                onboardingscreen3()
            )
            fragmentTransaction.addToBackStack(null) // Optional: Add to the back stack for back navigation
            fragmentTransaction.commit()

        }

        return view

    }
}