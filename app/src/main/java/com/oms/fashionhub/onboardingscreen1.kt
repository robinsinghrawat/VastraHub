package com.oms.fashionhub

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.card.MaterialCardView


class onboardingscreen1 : Fragment() {
    lateinit var textView: TextView
    lateinit var button: MaterialCardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboardingscreen1, container, false)
        textView=view.findViewById(R.id.text2)
        val text = " <font color='#5B3822'>Seamless</font> <font color='#000000'>Shopping Experience </font>"

        textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        // Inflate the layout for this fragment

        button=view.findViewById(R.id.next)
        button.setOnClickListener {
            val fragmentTransaction = requireFragmentManager().beginTransaction()
            fragmentTransaction.replace(
                R.id.fragmentContainer, // Replace with the container ID of your fragments
                onboardingscreen2()
            )
            fragmentTransaction.addToBackStack(null) // Optional: Add to the back stack for back navigation
            fragmentTransaction.commit()

        }

        return view
    }


}