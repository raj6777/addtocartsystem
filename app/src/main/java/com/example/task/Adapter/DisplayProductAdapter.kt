package com.example.task.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.model.dbEntity


class DisplayProductAdapter(
    private var dataList: List<dbEntity>,
    private var listener: onSingleItemClicked,


) : RecyclerView.Adapter<DisplayProductAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_items, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recyclerModel = dataList[position]
        holder.productname.text = recyclerModel.ProductName
        holder.itemView.setOnClickListener {
            listener.onSelected(recyclerModel)
        }
    }
    override fun getItemCount(): Int {
        return dataList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val productname: TextView = itemView.findViewById(R.id.quoteText)
    }

    interface onSingleItemClicked {
        fun onSelected(data: dbEntity)

    }

}
