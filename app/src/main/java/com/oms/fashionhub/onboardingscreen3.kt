package com.oms.fashionhub

import android.content.Intent
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.card.MaterialCardView

class onboardingscreen3 : Fragment() {
    lateinit var textView: TextView
    lateinit var button: MaterialCardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_onboardingscreen3, container, false)
        textView=view.findViewById(R.id.text5)
        val text = "  <font color='#5B3822'>Swift</font> <font color='#000000'>and</font> <font color='#5B3822'>Reliable</font> <font color='#000000'>Delivery</font>"

        textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        // Inflate the layout for this fragment


        button=view.findViewById(R.id.next3)
        button.setOnClickListener {
            val intent = Intent(activity, registration::class.java)
            startActivity(intent)
            requireActivity().finish()

        }

        return view
    }

}