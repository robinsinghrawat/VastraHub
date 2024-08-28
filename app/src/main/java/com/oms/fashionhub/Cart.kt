package com.oms.fashionhub

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import org.json.JSONObject
import java.nio.charset.Charset

class Cart : Fragment(),deletecartprod {

    private lateinit var cartrecyler: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var cartfull: CardView
    lateinit var carttext: TextView
    lateinit var ttlprice: TextView
    lateinit var cartempty: ImageView
    lateinit var price: String
    private lateinit var cartlist: ArrayList<CartViewModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)

        cartrecyler = view.findViewById(R.id.cartrecycler)
        cartempty = view.findViewById(R.id.cartempty)
        cartfull = view.findViewById(R.id.cartfull)
        ttlprice = view.findViewById(R.id.ttlprice)

        cartfull.setOnClickListener {
            val intent = Intent(requireContext(), HomePage::class.java)
            startActivity(intent)
        }
        carttext = view.findViewById(R.id.noitemcart)
        val cartlayoutmanager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        cartrecyler.layoutManager = cartlayoutmanager
        cartlist = ArrayList()
        cartAdapter = CartAdapter(requireContext(), cartlist,this)
        cartrecyler.adapter = cartAdapter

        val queue = Volley.newRequestQueue(requireContext())
        sharedPreferences =
            requireActivity().getSharedPreferences("session", AppCompatActivity.MODE_PRIVATE)
        val userid = sharedPreferences.getString("id", "0")
        var totalPrice = 0

        val requestBody = ""
        val stringReq2: StringRequest = object : StringRequest(
            Request.Method.GET,
            "https://robinrawat.in.net/Admin/mycartapi.php?id=$userid",
            Response.Listener { response ->
                val jsonObject = JSONObject(response)
                if (jsonObject.isNull("data") || jsonObject.optString("data") == "null") {
                    // Response data is null, display image
                    val gifUrl = R.drawable.cart_empty // Replace with your GIF URL
                    Glide.with(this)
                        .asGif()
                        .load(gifUrl)
                        .into(cartempty)
                    cartempty.visibility = View.VISIBLE
                    cartfull.visibility = View.VISIBLE
                    carttext.visibility = View.VISIBLE
                } else {
                    val jsonArray = jsonObject.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val itemimage =
                            "https://robinrawat.in.net/Admin/" + jsonObject.getString("prodimage")
                        val brand = jsonObject.getString("brand")
                        val itemname = jsonObject.getString("prodname")
                        val cartid = jsonObject.getString("cartid")
                        price = jsonObject.getString("ttlprice")
                        val disprice = jsonObject.getString("disprice")

                        totalPrice += price.toInt()

                        cartlist.add(CartViewModel(itemimage, brand, itemname, price, disprice,cartid))
                    }

                  //  ttlprice.setText(totalPrice)
                    // Ensure that the adapter is set outside the loop
                    cartrecyler.adapter = cartAdapter
                    cartempty.visibility = View.GONE
                    cartfull.visibility = View.GONE
                    carttext.visibility = View.GONE
                    cartAdapter.notifyDataSetChanged()
                }
            },
            Response.ErrorListener { error ->
                Log.i("mylog", "error = $error")
            }) {

            override fun getBody(): ByteArray {
                return requestBody.toByteArray(Charset.defaultCharset())
            }
        }

        queue.add(stringReq2)

        return view
    }

    override fun onRemoveClicked(position: Int) {
        var cartid = cartlist[position].cartid.toString()

        Toast.makeText(requireContext(), ""+cartid, Toast.LENGTH_SHORT).show()
        val price = cartlist[position].cartprice.toInt() // Retrieve price here
        Toast.makeText(requireContext(), ""+price, Toast.LENGTH_SHORT).show()

        val queue = Volley.newRequestQueue(requireContext())
        val requestBody = ""
        val stringreq1: StringRequest =
            object : StringRequest(
                Method.POST,
                "https://www.robinrawat.in.net/Admin/deleteprodcart.php",
                Response.Listener { response ->
                    // Remove the item from the cartlist locally
                    cartlist.removeAt(position)
                    // Notify the adapter about the change
                    cartAdapter.notifyItemRemoved(position)
                    // Update the total price
                    updateTotalPrice(price)
                    // Update the item count
                    Toast.makeText(requireContext(), "$response", Toast.LENGTH_SHORT).show()
                },
                Response.ErrorListener { error ->
                    Toast.makeText(requireContext(), "" + error, Toast.LENGTH_SHORT).show()
                }) {

                override fun getParams(): MutableMap<String, String>? {
                    val params = HashMap<String, String>()
                    params["cartid"] = cartid
                    return params;
                }
            }
        queue.add(stringreq1)
    }

    private fun updateTotalPrice(price: Int) {
        val currentPrice = ttlprice.text.toString().substring(1).toInt()
        val totalPrice = currentPrice - price
        ttlprice.text = "₹$totalPrice"
    }


}
