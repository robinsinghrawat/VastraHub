package com.oms.fashionhub

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONException
import org.json.JSONObject

class EditProfile : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userId: String
    private lateinit var nameEditText: TextInputEditText
    private lateinit var emailEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var imageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("session", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("id", "0") ?: "0"

        nameEditText = view.findViewById(R.id.profilename)
        emailEditText = view.findViewById(R.id.useremail)
        passwordEditText = view.findViewById(R.id.userpassword)
        imageView = view.findViewById(R.id.userimage)

        val queue = Volley.newRequestQueue(requireContext())

        val stringRequest = StringRequest(
            Request.Method.GET, "https://robinrawat.in.net/profileedit.php?id=$userId",
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response)

                    val name = jsonObject.getString("name")
                    val email = jsonObject.getString("email")
                    val password = jsonObject.getString("password")

                    nameEditText.setText(name)
                    emailEditText.setText(email)
                    passwordEditText.setText(password)

                    val image = "https://robinrawat.in.net/" + jsonObject.getString("profileimage")
                    Glide.with(requireContext())
                        .load(image)
                        .into(imageView)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Log.i("mylog", "error = $error")
            })

        queue.add(stringRequest)

        val updateButton: MaterialCardView = view.findViewById(R.id.completeprofile)
        updateButton.setOnClickListener {
            updateProfile()
        }

        return view
    }

    private fun updateProfile() {
        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        val request = object : StringRequest(
            Method.POST, "https://www.robinrawat.in.net/profileapi.php",
            Response.Listener { response ->
                // Handle the response if needed
            },
            Response.ErrorListener { error ->
                Log.i("mylog", "error = $error")
            }) {

            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["id"] = userId
                params["name"] = name
                params["email"] = email
                params["password"] = password
                // Add other parameters as needed
                return params
            }
        }

        Volley.newRequestQueue(requireContext()).add(request)
    }
}