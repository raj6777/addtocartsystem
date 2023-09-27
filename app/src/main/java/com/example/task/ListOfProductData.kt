package com.example.task

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.task.Adapter.OrderListingAdapter
import com.example.task.model.OrderModel
import com.example.task.room.ProductItemDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListOfProductData : AppCompatActivity(), OrderListingAdapter.onSingleItemClicked {
    var orderlist = ArrayList<OrderModel>()
    lateinit var database: ProductItemDatabase
    lateinit var rv: RecyclerView
    lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listofdata)
        rv = findViewById(R.id.orderlistingrecycler)
        database = Room.databaseBuilder(
            applicationContext,
            ProductItemDatabase::class.java, "productDB"
        ).build()
        GlobalScope.launch {
            for (i in database.orderdao().getdataoforder() as ArrayList<OrderModel>) {
                orderlist.add(i)
            }
        }

        showProductData()
    }

    fun showProductData() {
        val adapter = OrderListingAdapter(orderlist, this)
        rv.layoutManager = LinearLayoutManager(this@ListOfProductData)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onSelected(data: OrderModel) {
        val intent = Intent(this@ListOfProductData, DisplayDataActivity::class.java)
        intent.putExtra("id", data.orderid)
        this@ListOfProductData.startActivity(intent)
    }
}