package com.oms.fashionhub

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset


class HomeScrreen : Fragment(),RecyclerViewItemClickListener {

    private lateinit var recyclerView3: RecyclerView
    lateinit var username: TextView
    lateinit var mycart:ImageView
    private lateinit var circularIndicatorLayout: LinearLayout
    private var clickedItem: SubcategoryViewModel? = null
    private lateinit var arrayList3: ArrayList<BannerItemModel>
    lateinit var  subarrayList : ArrayList<SubcategoryViewModel>
    private lateinit var adapter3: BannerRecyclerViewAdapter
    private var selectedPosition: Int = 0
    lateinit var catname1:String
    lateinit var menid1:String
    lateinit var sharedPreferences: SharedPreferences
    lateinit var profile: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_scrreen, container, false)

        username = view.findViewById(R.id.username)
        mycart = view.findViewById(R.id.mycart)
        mycart.setOnClickListener {
            var intent = Intent(requireContext(),MyCart::class.java)
            startActivity(intent)
        }
////PROFILE API WORK
        val queuename = Volley.newRequestQueue(requireContext())
        sharedPreferences =
            requireActivity().getSharedPreferences("session", AppCompatActivity.MODE_PRIVATE)
        var userid = sharedPreferences.getString("id", "0")
        var ee=sharedPreferences.getString("kk","0")

        val stringReqname: StringRequest =
            object : StringRequest(
                Method.POST, "https://www.robinrawat.in.net/profileapi.php",
                Response.Listener { response ->

                    try {
                        val jsonObject = JSONObject(response)
                        val name = jsonObject.getString("name")
                        username.text = name
                    } catch (e: JSONException) {
                        Log.e("mylog", "Error parsing JSON: $e")
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

  ///BANNER API WORK

        recyclerView3 = view.findViewById(R.id.bannerrecycler)
        circularIndicatorLayout = view.findViewById(R.id.circularIndicatorLayout)

        val layoutManager3 = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recyclerView3.layoutManager = layoutManager3

        arrayList3 = ArrayList()
        adapter3 = BannerRecyclerViewAdapter(requireContext(), arrayList3)
        recyclerView3.adapter = adapter3

        val queue3 = Volley.newRequestQueue(requireContext())

        val requestBody3 = ""
        val stringReq3: StringRequest =
            object : StringRequest(
                Request.Method.POST, "https://robinrawat.in.net/bannershow.php",
                Response.Listener { response ->

                    var jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        var jsonObject = jsonArray.getJSONObject(i)
                        var itemimage = "https://robinrawat.in.net/" + jsonObject.getString("bannerimg")
                        arrayList3.add(BannerItemModel(itemimage))
                    }

                    adapter3.notifyDataSetChanged()
                    addCircularIndicators(arrayList3.size, circularIndicatorLayout)

                    recyclerView3.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                            super.onScrolled(recyclerView, dx, dy)
                            val firstVisibleItem =
                                layoutManager3.findFirstVisibleItemPosition()
                            if (selectedPosition != firstVisibleItem) {
                                selectedPosition = firstVisibleItem
                                updateIndicators()
                            }
                        }
                    })

                },
                Response.ErrorListener { error ->
                    Log.i("mylog", "error = $error")
                }) {

                override fun getBody(): ByteArray {
                    return requestBody3.toByteArray(Charset.defaultCharset())
                }
            }

        queue3.add(stringReq3)

////CATEGORY API WORK HERE

        val recyclerView: RecyclerView = view.findViewById(R.id.item_recyclerview)
        recyclerView.layoutManager = GridLayoutManager(context,3)

        val itemList = listOf(
            CategoryViewModel(R.drawable.male.toString(), "MENS"),
            CategoryViewModel(R.drawable.female.toString(), "Womens"),
            CategoryViewModel(R.drawable.child.toString(), "BOYS & GIRLS "),

            // Add more items as needed
        )

        val itemAdapter = CategoryRecyclerViewAdapter(requireContext(), itemList)
        recyclerView.adapter = itemAdapter

        itemAdapter.setOnItemClickListener(object : CategoryRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Handle item click here
                val selectedItem = itemList[position]
                if (selectedItem.text == "MENS") {

                    val mens=MensrFragment()
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.mainContent, mens)
                    transaction.addToBackStack(null)
                    transaction.commit()

                    // Do something specific for MENS category
                }
                else if(selectedItem.text=="Womens"){
                    val women=WomenFragment()
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.mainContent, women)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
                else{
                    val child=ChildFragmernt()
                    val transaction = requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.mainContent, child)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }

            }        })


       /* val queue = Volley.newRequestQueue(requireContext())

        val requestBody4 = ""
        val stringReq4: StringRequest =
            object : StringRequest(
                Method.POST, "https://robinrawat.online/categoryapi.php",
                Response.Listener { response ->

                    var jsonArray = JSONArray(response)
                    for (i in 0 until jsonArray.length()) {
                        var jsonObject = jsonArray.getJSONObject(i)
                        var itemimage = "https://robinrawat.online/Admin/" + jsonObject.getString("cimage")
                        var itemname = jsonObject.getString("cname")
                        arrayList.add(CategoryViewModel(itemimage, itemname))
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

        queue.add(stringReq4)*/

 ////SUBCATEGORY API WORK

        val subrecyclerView = view.findViewById<RecyclerView>(R.id.subcatrecycler)
        val sublayoutManager = GridLayoutManager(requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        subrecyclerView.layoutManager = sublayoutManager
        subarrayList = ArrayList<SubcategoryViewModel>()
        val subadapter = SubcategoryAdapter(requireContext(), subarrayList ,this)

        subrecyclerView.adapter = subadapter


        val queue2 = Volley.newRequestQueue(requireContext())

        val requestBody2 = ""
         val stringReq2: StringRequest = object : StringRequest(
            Method.POST, "https://robinrawat.in.net/Admin/subcategory.php",
            Response.Listener { response ->

                var jsonArray = JSONArray(response)
                for (i in 0 until jsonArray.length()) {
                    var jsonObject = jsonArray.getJSONObject(i)
                    var itemimage = "https://robinrawat.in.net/Admin/" + jsonObject.getString("msimage")
                    var brand = jsonObject.getString("brand")
                    var itemname = jsonObject.getString("msname")
                    var price = jsonObject.getString("price")
                    val disprice = jsonObject.getString("disprice")
                    catname1 = jsonObject.getString("catname")
                    menid1 = jsonObject.getString("menid")



                    subarrayList.add(SubcategoryViewModel(itemimage, brand, itemname, price, disprice,catname1,menid1))
                }

                // Ensure that the adapter is set outside the loop
                subrecyclerView.adapter = subadapter
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




    private fun addCircularIndicators(count: Int, layout: LinearLayout) {
        for (i in 0 until count) {
            val circularImageView = createCircularImageView(i == 0)
            layout.addView(circularImageView)
        }
    }

    private fun createCircularImageView(isSelected: Boolean): ImageView {
        val imageView = ImageView(requireContext())
        val layoutParams = LinearLayout.LayoutParams(
            resources.getDimensionPixelSize(R.dimen.circular_image_size),
            resources.getDimensionPixelSize(R.dimen.circular_image_size)
        )
        layoutParams.marginEnd = resources.getDimensionPixelSize(R.dimen.circular_image_margin)
        imageView.layoutParams = layoutParams

        if (isSelected) {
            imageView.setBackgroundResource(R.drawable.tab_indicator_selected)
        } else {
            imageView.setBackgroundResource(R.drawable.tab_indicator_unselected)
        }

        return imageView
    }

    private fun updateIndicators() {
        for (i in 0 until circularIndicatorLayout.childCount) {
            val imageView = circularIndicatorLayout.getChildAt(i) as ImageView
            imageView.setBackgroundResource(
                if (i == selectedPosition) R.drawable.tab_indicator_selected
                else R.drawable.tab_indicator_unselected
            )
        }
    }

    override fun onItemClicked(position: Int) {

        sharedPreferences=requireContext().getSharedPreferences("session2",AppCompatActivity.MODE_PRIVATE)
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
