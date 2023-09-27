package com.example.apiintegration.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder

import com.example.task.R
import com.example.task.model.CartItemModel
import com.example.task.model.dbEntity

class AddToCartAdapter(private val dataList: List<CartItemModel>) : RecyclerView.Adapter<AddToCartAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddToCartAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.addtocart_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recyclerModel = dataList[position]

        // Text set to the TextView widget
        holder.P_name.text = recyclerModel.dbEntity.ProductName
        holder.P_qty.text = "Qty: "+recyclerModel.quantity.toString()
        holder.P_price.text = "Selling price: "+recyclerModel.dbEntity.SellingRate
        holder.innerTotal.text = "Total Price: "+recyclerModel.quantity * recyclerModel.dbEntity.SellingRate.toInt()

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