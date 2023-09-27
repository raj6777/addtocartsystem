package com.example.task.Adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.model.OrderModel
import java.text.SimpleDateFormat
import java.util.*


class OrderListingAdapter(
    private var dataList: List<OrderModel>,
    private var listener: onSingleItemClicked,
    ) : RecyclerView.Adapter<OrderListingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listofdata_item, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recyclerModel = dataList[position]
        holder.orderid.text = recyclerModel.orderid
        holder.ordertotal.text=recyclerModel.totalProductCount
        var simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        holder.ordertime.text=simpleDateFormat.format(Date(recyclerModel.creatAt.toLong()))

        //var createtime=System.currentTimeMillis()

        holder.itemView.setOnClickListener {
            listener.onSelected(recyclerModel)
        }
    }
    override fun getItemCount(): Int {
        return dataList.size
    }
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val orderid: TextView = itemView.findViewById(R.id.orderid)
        val ordertotal:TextView=itemView.findViewById(R.id.ordertotal)
        val ordertime:TextView=itemView.findViewById(R.id.ordertime)
    }

    interface onSingleItemClicked {
        fun onSelected(data: OrderModel)

    }

}
