package com.oms.fashionhub

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import org.json.JSONArray
import java.nio.charset.Charset

class MensrFragment : Fragment(),RecyclerViewItemClickListener,recycleClick {
    lateinit var catname1:String
    lateinit var menid1:String
    lateinit var subarrayList :ArrayList<SubcategoryViewModel>

    lateinit var sharedPreferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_mensr, container, false)
        val gifImageView: ImageView = view.findViewById(R.id.mengif)

        Glide.with(this)
            .asGif()
            .load(R.drawable.menss) // Replace with the actual GIF resource ID
            .placeholder(R.drawable.ic_launcher_foreground) // Replace with a placeholder image resource ID
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(gifImageView)

        val recyclerView: RecyclerView = view.findViewById(R.id.menitemrecycle)
        val layoutManager3 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager3

        val arrayList = ArrayList<MenViewModel>()
        val adapter = MenViewAdapter(requireContext(), arrayList,this)

        val queue = Volley.newRequestQueue(requireContext())

        val requestBody4 = ""
        val stringReq4: StringRequest =
            object : StringRequest(
                Method.POST, "https://robinrawat.in.net/men.php",
                Response.Listener { response ->

                    var jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        var jsonObject = jsonArray.getJSONObject(i)
                        var itemimage = "https://robinrawat.in.net/Admin/" + jsonObject.getString("mimage")
                        var itemname = jsonObject.getString("mcatname")
                        arrayList.add(MenViewModel(itemimage, itemname))
                        recyclerView.adapter = adapter
                    }

                },
                Response.ErrorListener { error ->
                    Log.i("mylog", "error = $error")
                }) {

                override fun getBody(): ByteArray {
                    return requestBody4.toByteArray(Charset.defaultCharset())
                }
            }

        queue.add(stringReq4)


   /////MENS SUBCAT WORK

        val subrecyclerView = view.findViewById<RecyclerView>(R.id.mensubrecycler)
        val sublayoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        subrecyclerView.layoutManager = sublayoutManager
        subarrayList = ArrayList<SubcategoryViewModel>()
        val subadapter = SubcategoryAdapter(requireContext(), subarrayList,this)
        val queue2 = Volley.newRequestQueue(requireContext())

        val requestBody2 = ""
        val stringReq2: StringRequest =
            object : StringRequest(
                Method.POST, "https://robinrawat.in.net/Admin/mensubcat.php",
                Response.Listener { response ->

                    var jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        var jsonObject = jsonArray.getJSONObject(i)
                        var itemimage = "https://robinrawat.in.net/Admin/" + jsonObject.getString("msimage")
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
                    Log.i("mylog", "error = $error")
                }) {

                override fun getBody(): ByteArray {
                    return requestBody2.toByteArray(Charset.defaultCharset())
                }
            }

        queue2.add(stringReq2)


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

    override fun onItemclick(position: Int) {
        val nextpage=Aftersearch()
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.mainContent, nextpage)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}