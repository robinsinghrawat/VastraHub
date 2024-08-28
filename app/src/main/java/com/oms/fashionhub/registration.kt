package com.oms.fashionhub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.card.MaterialCardView
import org.json.JSONObject
import java.lang.reflect.Method

class registration : AppCompatActivity() {
    lateinit var textView: TextView
    lateinit var button: MaterialCardView
    lateinit var ed:EditText
    lateinit var ed1:EditText
    lateinit var ed2:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        textView=findViewById(R.id.signintext)
        button=findViewById(R.id.signup)

        //signup api

        ed= findViewById(R.id.name)
        ed1=findViewById(R.id.email)
        ed2=findViewById(R.id.password)

        val text = " <font color='#000000'>Already have an account? </font> <font color='#5B3822'>Sign In</font>"
        textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)
        textView.setOnClickListener {
            var intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        //navigate to signin
        val queue= Volley.newRequestQueue(this)
        button.setOnClickListener {
            var name=ed.text.toString().trim();
            var email=ed1.text.toString().trim();
            var password=ed2.text.toString().trim();

            var stringReq : StringRequest =object : StringRequest(
                Method.POST,"https://www.robinrawat.in.net/registrationpage.php",

                Response.Listener { response ->

                    var jsonobj=JSONObject(response)

                    var ms=jsonobj.getString("msg")

                    if (ms.equals("User Already Exists")){

                        Toast.makeText(this, ""+ms, Toast.LENGTH_SHORT).show()
                    }
                    else if(ms.equals("Data Inserted")){
                        Toast.makeText(this, ""+ms, Toast.LENGTH_SHORT).show()
                        var intent = Intent(this, LoginPage::class.java)
                        startActivity(intent)
                    }

                },
                Response.ErrorListener { error ->

                    Toast.makeText(this, ""+error, Toast.LENGTH_SHORT).show()
                }){
                override fun getParams(): MutableMap<String, String>? {
                    val params=HashMap<String,String>()
                    params["name"]=name;
                    params["email"]=email;
                    params["password"]=password;
                    return params;
                }
            }
            queue.add(stringReq)

        }
    }
}