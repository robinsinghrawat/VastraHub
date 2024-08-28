package com.oms.fashionhub

import android.app.LauncherActivity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class profileadapter(private val context: Context, private val itemList: List<profileitem>) : BaseAdapter() {

    override fun getCount(): Int {
        return itemList.size
    }

    override fun getItem(position: Int): Any {
        return itemList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.profileitem, parent, false)

        val item = itemList[position]

        val itemImage: ImageView = view.findViewById(R.id.itemImage)
        itemImage.setImageResource(item.profileimage)

        val itemTitle: TextView = view.findViewById(R.id.itemTitle)
        itemTitle.text = item.text

        return view
    }
}
