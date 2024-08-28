package com.oms.fashionhub

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView

class BannerRecyclerViewAdapter(private val context: Context, private val list: ArrayList<BannerItemModel>) :RecyclerView.Adapter<BannerRecyclerViewAdapter.MyViewHolder>(){
    class MyViewHolder(Banneritem:View):RecyclerView.ViewHolder(Banneritem) {
        val bannerimage:ImageView=Banneritem.findViewById(R.id.bannerimg)
        val bannerCard1: MaterialCardView = Banneritem.findViewById(R.id.bannercard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.bannerapi,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val itemViewModel3=list[position]
        Glide.with(context).load(itemViewModel3.image).into(holder.bannerimage)


    }

}





