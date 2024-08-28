package com.oms.fashionhub

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONArray
import java.nio.charset.Charset

class Search : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var imageView: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        imageView=view.findViewById(R.id.searchicon)

        val searchtext = view.findViewById<TextInputEditText>(R.id.search)
        imageView.setOnClickListener {
            val mens = Aftersearch()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mainContent, mens)
            transaction.addToBackStack(null)
            transaction.commit()

            sharedPreferences =
                requireActivity().getSharedPreferences("session", AppCompatActivity.MODE_PRIVATE)

            val editor = sharedPreferences.edit()
            editor.putString("brand", searchtext.text.toString())
            editor.apply()

        }

/////BRANDVIEW API WORK


        val subrecyclerView = view.findViewById<RecyclerView>(R.id.brandrecycle)
        val sublayoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        subrecyclerView.layoutManager = sublayoutManager
        val subarrayList = ArrayList<BrandViewModel>()
        val subadapter = BrandViewAdapter(requireContext(), subarrayList) { brandName ->
            val mens = Aftersearch()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mainContent, mens)
            transaction.addToBackStack(null)
            transaction.commit()

            sharedPreferences =
                requireActivity().getSharedPreferences("session1", AppCompatActivity.MODE_PRIVATE)

            val editor = sharedPreferences.edit()
            editor.putString("brand", brandName)
            editor.apply()
        }
        subrecyclerView.adapter = subadapter
        val queue2 = Volley.newRequestQueue(requireContext())

        val requestBody2 = ""
        val stringReq2: StringRequest =
            object : StringRequest(
                Method.POST, "https://robinrawat.in.net/displaybrand.php",
                Response.Listener { response ->

                    var jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        var jsonObject = jsonArray.getJSONObject(i)
                        var itemimage = "https://robinrawat.in.net/Admin/" + jsonObject.getString("bimage")
                        var brandname =jsonObject.getString("brand")
                        subarrayList.add(BrandViewModel(itemimage,brandname))


                    }

                    // Move this line outside of the loop to set the adapter only once
                    subrecyclerView.adapter = subadapter
                    // Notify the adapter that the data set has changed
                    subadapter.notifyDataSetChanged()



                },
                Response.ErrorListener { error ->
                    Log.i("mylog", "error = $error")
                }) {

                override fun getBody(): ByteArray {
                    return requestBody2.toByteArray(Charset.defaultCharset())
                }
            }

        queue2.add(stringReq2)

        return view
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
