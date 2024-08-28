package com.oms.fashionhub

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.card.MaterialCardView
import org.json.JSONObject

class LoginPage : AppCompatActivity() {
    lateinit var textView: TextView
    lateinit var login:MaterialCardView
    lateinit var ed1: EditText
    lateinit var ed2: EditText
    lateinit var sharedPreferences: SharedPreferences;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        sharedPreferences = getSharedPreferences("session", MODE_PRIVATE)


        var ee=sharedPreferences.getString("kk","0")
        if (ee!="0"){
            startActivity(Intent(this,HomePage::class.java))
        }

        ed1 = findViewById(R.id.loginemail)
        ed2 = findViewById(R.id.loginpass)
        login = findViewById(R.id.signin)

        textView= findViewById(R.id.signuptext)


        val text = " <font color='#000000'>Don't have an account? </font> <font color='#5B3822'>Sign Up</font>"
        textView.text = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY)

        textView.setOnClickListener {
            var intent = Intent(this, registration  ::class.java)
            startActivity(intent)
            finish()
        }

        //LOGIN Api work

        val queue = Volley.newRequestQueue(this)
        login.setOnClickListener {
            var email2 = ed1.text.toString().trim()
            var password2 = ed2.text.toString().trim()

            val stringreq1: StringRequest =
                object : StringRequest(
                    Method.POST,
                    "https://robinrawat.in.net/loginapi.php",
                    Response.Listener { response ->

                        var jsonObject= JSONObject(response);

                        var name=jsonObject.getString("message")
                        var userid=jsonObject.getString("id")
                        if(name == "Login successful.") {
                            val editor=sharedPreferences.edit()
                            editor.putString("id",""+userid)
                            editor.apply();
                            Toast.makeText(this, ""+name, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, HomePage::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
                        }

                    },
                    Response.ErrorListener { error ->

                        Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show()
                    }) {

                    override fun getParams(): MutableMap<String, String>? {
                        val params = HashMap<String, String>()
                        params["email"] = email2;
                        params["password"] = password2;

                        return params;

                    }
                }
            queue.add(stringreq1)

        }

    }
}