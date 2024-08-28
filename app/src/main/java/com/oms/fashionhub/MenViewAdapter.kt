package com.oms.fashionhub

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MenViewAdapter(private val context: Context, private val list: List<MenViewModel>,private val itemClickListener: recycleClick):
        RecyclerView.Adapter<MenViewAdapter.MenViewHolder> () {
            class MenViewHolder(menitemview:View):RecyclerView.ViewHolder(menitemview) {
        val subimage: ImageView =menitemview.findViewById(R.id.catimage)
        val textView: TextView = itemView.findViewById(R.id.catname)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.categapi,parent,false)
        return MenViewAdapter.MenViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MenViewHolder, position: Int) {
        val itemViewModel=list[position]
        Glide.with(context).load(itemViewModel.image).into(holder.subimage)

        holder.textView.text=itemViewModel.text
        holder.subimage.setOnClickListener {
            itemClickListener.onItemclick(position)
        }
    }
}