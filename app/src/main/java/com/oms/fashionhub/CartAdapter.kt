package com.oms.fashionhub

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView

class CartAdapter(private val context: Context, private val list: List<CartViewModel>,private val deletecartprod: deletecartprod):RecyclerView.Adapter<CartAdapter.MyViewHolder>() {
    class MyViewHolder(itemview:View):RecyclerView.ViewHolder(itemview) {
        val cartimage:ImageView = itemview.findViewById(R.id.cartimage)
        val cartbrandname:TextView = itemview.findViewById(R.id.cartprodname)
        val cartname:TextView = itemview.findViewById(R.id.cartname)
        val cartprice:TextView = itemview.findViewById(R.id.cartprice)
        val cartdisprice:TextView = itemview.findViewById(R.id.cartdisprice)
        val removeprod:MaterialCardView = itemview.findViewById(R.id.removeprod)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemview = list[position]
        Glide.with(context).load(itemview.cartimage).into(holder.cartimage)
        holder.cartbrandname.text = itemview.cartprodname
        holder.cartname.text = itemview.cartname
        holder.cartdisprice.text = itemview.cartdisprice
        holder.cartprice.text = itemview.cartprice

        holder.removeprod.setOnClickListener {
            deletecartprod.onRemoveClicked(position)
        }
    }
}