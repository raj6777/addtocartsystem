package com.example.task.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.apiintegration.adapter.AddToCartAdapter
import com.example.task.R
import com.example.task.model.CartItemModel
import com.example.task.model.OrderAndOrderProductModel


class DisplaySingleUserDetailPageAdapter (private val dataList: List<OrderAndOrderProductModel>) : RecyclerView.Adapter<DisplaySingleUserDetailPageAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DisplaySingleUserDetailPageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.displaydataitemforsingleuser, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recyclerModel = dataList[position]
        // Text set to the TextView widget
        holder.P_name.text = recyclerModel.productname
        holder.P_qty.text = "Qty: "+recyclerModel.productqty
        holder.P_price.text = "Selling price: "+recyclerModel.productsellingrate
        holder.innerTotal.text = "Total Price: "+recyclerModel.productqty * recyclerModel.productsellingrate.toInt()

    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val P_name: TextView = itemView.findViewById(R.id.productname)
        val P_qty: TextView = itemView.findViewById(R.id.productqty)
        val innerTotal: TextView = itemView.findViewById(R.id.innerTotal)
        val P_price: TextView = itemView.findViewById(R.id.productprice)

    }
}