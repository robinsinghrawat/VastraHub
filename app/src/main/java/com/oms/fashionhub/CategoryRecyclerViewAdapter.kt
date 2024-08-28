package com.oms.fashionhub

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryRecyclerViewAdapter(
    private val context: Context?,
    private val itemList: List<CategoryViewModel>
) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.catimage)
        val textView: TextView = itemView.findViewById(R.id.catname)

        init {
            imageView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categapi, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]

        // Set image and text
        holder.imageView.setImageResource(item.image.toInt())
        holder.textView.text = item.text
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
