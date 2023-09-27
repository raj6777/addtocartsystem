package com.example.task

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.task.Adapter.DisplaySingleUserDetailPageAdapter
import com.example.task.model.OrderAndOrderProductModel
import com.example.task.room.ProductItemDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DisplayDataActivity : AppCompatActivity() {
    var combaine = ArrayList<OrderAndOrderProductModel>()
    lateinit var database: ProductItemDatabase
    lateinit var rv: RecyclerView
    lateinit var finalid: String
    lateinit var finalamount: TextView
    lateinit var username: TextView
    lateinit var contectno: TextView
    lateinit var date: TextView
    lateinit var time: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_displaydataacticity)
        rv = findViewById(R.id.singleuserdatarecycler)
        finalid = intent.getStringExtra("id").toString()
        finalamount = findViewById(R.id.finalamount)
        username = findViewById(R.id.username)
        contectno = findViewById(R.id.contectno)
        date = findViewById(R.id.date)
        time = findViewById(R.id.time)
        database = Room.databaseBuilder(
            applicationContext,
            ProductItemDatabase::class.java, "productDB"
        ).build()
        GlobalScope.launch {
            for (j in database.oproductdao().fetchparticulardata()) {
                if (finalid == j.orderid) {
                    combaine.add(j)
                    finalamount.text = "Net Amount = " + j.totalAmount.toString()
                    username.text = "UserName :" + j.username
                    contectno.text = "ContectNo :" + j.contectno
                    var simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                    date.text = "Date :" + simpleDateFormat.format(Date(j.creatAt.toLong()))
                    var simpleTimeFormat = SimpleDateFormat("HH:mm:ss")
                    time.text = "Time :" + simpleTimeFormat.format(Date(j.creatAt.toLong()))


                }

            }
        }
        showParticularUserProductData()

    }

    fun showParticularUserProductData() {
        val adapter = DisplaySingleUserDetailPageAdapter(combaine)
        rv.layoutManager = LinearLayoutManager(this@DisplayDataActivity)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}