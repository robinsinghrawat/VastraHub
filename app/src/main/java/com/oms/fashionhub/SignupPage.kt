package com.oms.fashionhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import com.google.android.material.card.MaterialCardView

class SignupPage : AppCompatActivity() {
    lateinit var textView :TextView
    lateinit var textview2 :TextView
    lateinit var signup :MaterialCardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)
        textView=findViewById(R.id.textView)
        textview2=findViewById(R.id.alreadysignup)
        signup= findViewById(R.id.signup)
        val text = "<font color='#000000'>The</font> <font color='#5B3822'>Fashion App</font> <font color='#000000'>That <br> Makes You Look Your Best</font>"
        val text2 = "<font color='#000000'>Already have an account? </font> <font color='#5B3822'>Sign In</font>"
        // Use Html.fromHtml to parse the HTML-formatted text
        textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        textview2.text = Html.fromHtml(text2, Html.FROM_HTML_MODE_LEGACY)

        signup.setOnClickListener {
        var intent = Intent(this,Onboardingscreen::class.java)
        startActivity(intent)

        }

        textview2.setOnClickListener {
            var intent =Intent(this,LoginPage::class.java)
            startActivity(intent)
            finish()
        }
    }

}