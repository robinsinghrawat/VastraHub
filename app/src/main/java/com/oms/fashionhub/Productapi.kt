package com.oms.fashionhub

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset


class Productapi : Fragment() {

    lateinit var sharedPreferences: SharedPreferences

    lateinit var addcart:MaterialCardView
    lateinit var image:String
    lateinit var price:String
    lateinit var catname:String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_productapi, container, false)

        val pimage: ImageView = view.findViewById(R.id.prodimage)
        val pBrand: TextView = view.findViewById(R.id.brand)
        val pname: TextView = view.findViewById(R.id.productfulname)
        val pPrice: TextView = view.findViewById(R.id.Price)
        val pdisprice: TextView = view.findViewById(R.id.disPrice)
        val pdescription: TextView = view.findViewById(R.id.productdetail)







        /*val prodrecyclerView = view.findViewById<RecyclerView>(R.id.prodrecycle)
        val prodlayoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        prodrecyclerView.layoutManager = prodlayoutManager
        val prodarrayList = ArrayList<ProductViewModel>()
        val prodadapter = ProductViewAdapter(requireContext(), prodarrayList){ catName ->}
        sharedPreferences =
            requireActivity().getSharedPreferences("session2", AppCompatActivity.MODE_PRIVATE)
        var brand = sharedPreferences.getString("catname","")
        var menid = sharedPreferences.getString("menid","")
        val prodqueue = Volley.newRequestQueue(requireContext())


        val prodrequestBody = ""
        val prodstringReq: StringRequest =
            object : StringRequest(
                Method.GET, "https://robinrawat.online/Admin/productapi.php?catname=$brand&menid=$menid",
                Response.Listener { response ->

                    var jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        var jsonObject = jsonArray.getJSONObject(i)
                        var itemimage = "https://robinrawat.online/Admin/" + jsonObject.getString("msimage")
                        var brand = jsonObject.getString("brand")
                        var itemname = jsonObject.getString("msname")
                        var price = jsonObject.getString("price")
                        val disprice=jsonObject.getString("disprice")
                        val catname=jsonObject.getString("catname")
                        val description=jsonObject.getString("Pdescription")




                        prodarrayList.add(ProductViewModel(itemimage,brand,itemname,price,disprice,description))
                        prodrecyclerView.adapter = prodadapter
                    }

                },
                Response.ErrorListener { error ->
                    Log.i("mylog", "error = $error")
                }) {

                override fun getBody(): ByteArray {
                    return prodrequestBody.toByteArray(Charset.defaultCharset())
                }
            }

        prodqueue.add(prodstringReq)*/

        val queue = Volley.newRequestQueue(requireContext())

        sharedPreferences =
            requireActivity().getSharedPreferences("session2", AppCompatActivity.MODE_PRIVATE)
        var brand1 = sharedPreferences.getString("catname", "")
        var menid1 = sharedPreferences.getString("menid", "")


        val stringreq1: StringRequest =
            @SuppressLint("SetTextI18n")
            object : StringRequest(
                Method.POST,
                "https://robinrawat.in.net/Admin/productapi.php?catname=$brand1&menid=$menid1",
                Response.Listener { response ->

                    var jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        var jsonObject = jsonArray.getJSONObject(i)
                        var itemimage =
                            "https://robinrawat.in.net/Admin/" + jsonObject.getString("msimage")
                        var brand = jsonObject.getString("brand")
                        var itemname = jsonObject.getString("msname")
                        price = jsonObject.getString("price")
                        val disprice = jsonObject.getString("disprice")
                        image = jsonObject.getString("msimage")
                        catname = jsonObject.getString("catname")
                        val description = jsonObject.getString("Pdescription")
                        val finalItemImage = itemimage

                        Glide.with(requireContext()).load(itemimage).into(pimage)
                        /* pimage.setImageResource(itemimage.toInt())*/
                        pBrand.text = brand
                        pname.text = itemname
                        pPrice.text = "₹$price"
                        pdisprice.text = "₹$disprice"
                        pdescription.text = description


                    }

                },
                Response.ErrorListener { error ->

                    Toast.makeText(requireContext(), "" + error, Toast.LENGTH_SHORT).show()
                }) {


            }
        queue.add(stringreq1)

        //// ADD TO CART WORK
        addcart= view.findViewById(R.id.addcart)
        addcart.setOnClickListener {
            sharedPreferences =
                requireActivity().getSharedPreferences("session", AppCompatActivity.MODE_PRIVATE)
            var userid = sharedPreferences.getString("id", "0")
            val queue = Volley.newRequestQueue(requireContext())
            var stringReq : StringRequest =object : StringRequest(
                Method.POST,"https://robinrawat.in.net/Admin/addcartapi.php",

                Response.Listener { response ->

                    var jsonobj=JSONObject(response)

                    var ms=jsonobj.getString("message")

                    Toast.makeText(requireContext(), "$ms", Toast.LENGTH_SHORT).show()

                },
                Response.ErrorListener { error ->

                    Toast.makeText(requireContext(), ""+error, Toast.LENGTH_SHORT).show()
                }){
                override fun getParams(): MutableMap<String, String>? {
                    val params=HashMap<String,String>()
                    params["id"]=userid.toString()
                    params["menid"]=menid1.toString()
                    params["prodimage"]= image
                    params["brand"] = pBrand.text.toString()
                    params["prodname"]= pname.text.toString()
                    params["ttlprice"]= price
                    params["catname"] = catname;
                    params["disprice"]= pdisprice.text.toString()


                    return params;
                }
            }
            queue.add(stringReq)

        }

        return view
    }
}
