package com.oms.fashionhub

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject


class Profilework : Fragment() {
    lateinit var listView: ListView

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_profilework, container, false)
        val  user=view.findViewById<TextView>(R.id.user)
        val userimage=view.findViewById<ImageView>(R.id.userimage)


        val queuename = Volley.newRequestQueue(requireContext())
        sharedPreferences =
            requireActivity().getSharedPreferences("session", AppCompatActivity.MODE_PRIVATE)
        var userid = sharedPreferences.getString("id", "0")

        val stringReqname: StringRequest =
            object : StringRequest(
                Method.POST, "https://www.robinrawat.in.net/profileapi.php",
                Response.Listener { response ->

                    var jsonObject = JSONObject(response)
                    var name = jsonObject.getString("name")
                    var image = "https://robinrawat.in.net/"+jsonObject.getString("profileimage")
                    user.text = name
                    Glide.with(requireContext())
                        .load(image)
                        .into(userimage)

                    userimage.setOnClickListener {
                        // Show the enlarged image in a custom dialog
                        val dialog = ProfileImageDialog(requireContext(), image)
                        dialog.show()
                    }

                },
                Response.ErrorListener { error ->
                    Log.i("mylog", "error = $error")
                }) {

                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["id"] = userid.toString()

                    return params
                }
            }

        queuename.add(stringReqname)






        //profile page item lists
        val listView: ListView = view.findViewById(R.id.listview)

        val itemList = listOf(
            profileitem("My Profile", R.drawable.profileicon),
            profileitem("Payment Methods", R.drawable.cashpayment),
            profileitem("My Orders", R.drawable.myorders),
            profileitem("Settings", R.drawable.settings),
            profileitem("Help & Support", R.drawable.support),
            profileitem("Privacy Policy", R.drawable.privacypolicy),
            profileitem("Invites Friends", R.drawable.addfriend),
            profileitem("Log Out", R.drawable.log),


            // Add more items as needed
        )

        // Inside your onCreateView method, after setting the adapter
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = itemList[position]
            when (selectedItem.text) {
                "My Profile" -> {
                    // Navigate to My Profile page
                    val editProfileFragment = EditProfile()
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.mainContent, editProfileFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                "Log Out" -> {
                    // Navigate to Payment Methods page

                    logout()

                }
                // Add cases for other items as needed
                // ...
            }
        }



        val adapter = profileadapter(requireContext(), itemList)
        listView.adapter = adapter

        return view
    }

    private fun logout() {
        clearUserId()
        val intent = Intent(requireContext(), LoginPage::class.java)
        startActivity(intent)    }

    private fun clearUserId() {
        val editor = sharedPreferences.edit()
        editor.remove("id").apply()
    }

}