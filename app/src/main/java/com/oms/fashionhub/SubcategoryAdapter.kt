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

class SubcategoryAdapter(private val context: Context, private val list: List<SubcategoryViewModel>,private val itemClickListener: RecyclerViewItemClickListener) :
    RecyclerView.Adapter<SubcategoryAdapter.ScatViewHolder>() {


    class ScatViewHolder(subitemview: View) : RecyclerView.ViewHolder(subitemview) {


        val subimage: ImageView = subitemview.findViewById(R.id.subimage)
        val Brand: TextView = subitemview.findViewById(R.id.brand)
        val subname: TextView = subitemview.findViewById(R.id.subname)
        val Price: TextView = subitemview.findViewById(R.id.Price)
        val disprice: TextView = subitemview.findViewById(R.id.disPrice)
        val prodcard: MaterialCardView = subitemview.findViewById(R.id.prodcard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.subcategoryitem, parent, false)
        return ScatViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ScatViewHolder, position: Int) {
        val itemViewModel = list[position]
        Glide.with(context).load(itemViewModel.image).into(holder.subimage)
        holder.Brand.text = itemViewModel.brand
        holder.Price.text = itemViewModel.price
        holder.subname.text = itemViewModel.text
        holder.disprice.text = itemViewModel.disprice

        /*holder.itemView.setOnClickListener {
            onItemClick(itemViewModel.catname)

        }*/

        holder.prodcard.setOnClickListener {
            itemClickListener.onItemClicked(position)
        }


    }
}
