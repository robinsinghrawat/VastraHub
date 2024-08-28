package com.oms.fashionhub

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset

class Aftersearch : Fragment(),RecyclerViewItemClickListener {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var catname1:String
    lateinit var menid1:String
    lateinit var catname2:String
    lateinit var menid2:String
    lateinit var textView: TextView
    lateinit var subarrayList:ArrayList<SubcategoryViewModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view= inflater.inflate(R.layout.fragment_aftersearch, container, false)

        textView=view.findViewById(R.id.searchname)


        val preferences = requireContext().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)




        val subrecyclerView = view.findViewById<RecyclerView>(R.id.searchrecycler)
        val sublayoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        subrecyclerView.layoutManager = sublayoutManager
        subarrayList = ArrayList<SubcategoryViewModel>()
        val subadapter = SubcategoryAdapter(requireContext(), subarrayList,this)/*{ catName, menId ->}*/
        val queue2 = Volley.newRequestQueue(requireContext())

        sharedPreferences =
            requireActivity().getSharedPreferences("session", AppCompatActivity.MODE_PRIVATE)
        var userid = sharedPreferences.getString("brand","")
        textView.text=userid

        val requestBody2 = ""
        val stringReq2: StringRequest =
            object : StringRequest(
                Method.POST, "https://robinrawat.in.net/Admin/Searchapi.php?brand=$userid",
                Response.Listener { response ->

                    var jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        var jsonObject = jsonArray.getJSONObject(i)
                        var itemimage = "https://robinrawat.online/Admin/" + jsonObject.getString("msimage")
                        var brand = jsonObject.getString("brand")
                        var itemname = jsonObject.getString("msname")
                        var price = jsonObject.getString("price")
                        var disprice=jsonObject.getString("disprice")
                        catname1 = jsonObject.getString("catname")
                        menid1 = jsonObject.getString("menid")

                        subarrayList.add(SubcategoryViewModel(itemimage,brand,itemname,price,disprice,catname1,menid1))
                        subrecyclerView.adapter = subadapter
                    }

                },
                Response.ErrorListener { error ->
                    Toast.makeText(requireContext(), ""+error, Toast.LENGTH_SHORT).show()
                }) {

                override fun getBody(): ByteArray {
                    return requestBody2.toByteArray(Charset.defaultCharset())
                }
            }

        queue2.add(stringReq2)

////BRANDSEARCH BY IMAGE CLICK WORK

        val subrecyclerView2 = view.findViewById<RecyclerView>(R.id.searchrecycler)
        val sublayoutManager2 = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        subrecyclerView.layoutManager = sublayoutManager2
        subarrayList = ArrayList<SubcategoryViewModel>()
        val subadapter2 = SubcategoryAdapter(requireContext(), subarrayList,this)/*{ catName, menId ->}*/
        val queue = Volley.newRequestQueue(requireContext())

        sharedPreferences =
            requireActivity().getSharedPreferences("session1", AppCompatActivity.MODE_PRIVATE)
        var brand = sharedPreferences.getString("brand","")
        textView.text=brand

        val requestBody = ""
        val stringReq: StringRequest =
            object : StringRequest(
                    Method.POST, "https://robinrawat.in.net/Admin/brandsearchapi.php?brand=$brand",
                Response.Listener { response ->

                    var jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        var jsonObject = jsonArray.getJSONObject(i)
                        var itemimage = "https://robinrawat.online/Admin/" + jsonObject.getString("msimage")
                        var brand = jsonObject.getString("brand")
                        var itemname = jsonObject.getString("msname")
                        var price = jsonObject.getString("price")
                        var disprice=jsonObject.getString("disprice")
                        catname2 = jsonObject.getString("catname")
                        menid2 = jsonObject.getString("menid")

                        subarrayList.add(SubcategoryViewModel(itemimage,brand,itemname,price,disprice,catname2,menid2))
                        subrecyclerView2.adapter = subadapter2
                    }

                },
                Response.ErrorListener { error ->
                    Toast.makeText(requireContext(), ""+error, Toast.LENGTH_SHORT).show()
                }) {

                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }

        queue.add(stringReq)
        return view

    }

    override fun onItemClicked(position: Int) {
        sharedPreferences=requireContext().getSharedPreferences("session2", AppCompatActivity.MODE_PRIVATE)
        val editor=sharedPreferences.edit()
        editor.putString("catname",subarrayList[position].catname)
        editor.putString("menid",subarrayList[position].menid)
        editor.apply();
        val child=Productapi()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainContent, child)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}