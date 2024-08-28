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

class ProductViewAdapter(private val context: Context, private val plist:List<ProductViewModel>,private val onItemClick: (String) -> Unit):RecyclerView.Adapter<ProductViewAdapter.ProdViewHolder>() {
    class ProdViewHolder(pitemview: View):RecyclerView.ViewHolder(pitemview) {

        val pimage: ImageView =pitemview.findViewById(R.id.prodimage)
        val pBrand: TextView =pitemview.findViewById(R.id.brand)
        val pname: TextView =pitemview.findViewById(R.id.productfulname)
        val pPrice: TextView =pitemview.findViewById(R.id.Price)
        val pdisprice: TextView =pitemview.findViewById(R.id.disPrice)
        val pdescription:TextView=pitemview.findViewById(R.id.productdetail)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.productitem,parent,false)
        return ProductViewAdapter.ProdViewHolder(view)
    }

    override fun getItemCount(): Int {
       return plist.size
    }

    override fun onBindViewHolder(holder: ProdViewHolder, position: Int) {
        val itemViewModel=plist[position]
        Glide.with(context).load(itemViewModel.prodimage).into(holder.pimage)
        holder.pBrand.text=itemViewModel.prodbrand
        holder.pPrice.text=itemViewModel.prodprice
        holder.pname.text=itemViewModel.text
        holder.pdisprice.text=itemViewModel.proddisprice
        holder.pdescription.text=itemViewModel.proddesc
    }
}