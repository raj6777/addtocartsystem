package com.example.task

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.task.`interface`.user
import com.example.task.model.GenProduct
import com.example.task.model.dbEntity
import com.example.task.network.ApiClient
import com.example.task.room.ProductItemDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DisplayUserData : AppCompatActivity() {
    lateinit var database: ProductItemDatabase
    lateinit var seeAll: Button
    lateinit var progressbar:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_user_data)
        // Migration path definition from version 2 to version 3.
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
            }
        }
        seeAll = findViewById(R.id.see)
        database = Room.databaseBuilder(
            applicationContext,
            ProductItemDatabase::class.java, "productDB"
        )
            .addMigrations(MIGRATION_2_3)
            .fallbackToDestructiveMigration().allowMainThreadQueries()
            .build()

        database = Room.databaseBuilder(
            applicationContext,
            ProductItemDatabase::class.java, "productDB"
        ).allowMainThreadQueries().build()
        if (!database.productdao().getdata().isEmpty()) {
            seeAll.visibility = View.VISIBLE
        }

        val sharedPreference = getSharedPreferences("userData", Context.MODE_PRIVATE)
        var tx1: TextView = findViewById(R.id.textView)
        var tx2: TextView = findViewById(R.id.textView2)
        var tx3: TextView = findViewById(R.id.textView3)
        var tx4: TextView = findViewById(R.id.textView4)
        var tx5: TextView = findViewById(R.id.textView5)
        var tx6: TextView = findViewById(R.id.textView6)
        tx1.text = Html.fromHtml("<b>"+"Username: "+"</b>" + sharedPreference.getString("username", "").toString())
        tx2.text = Html.fromHtml("<b>"+"First name:"+"</b>"+ sharedPreference.getString("first", "").toString())
        tx3.text = Html.fromHtml("<b>"+"Last name:"+"</b>" + sharedPreference.getString("last", "").toString())
        tx4.text = Html.fromHtml("<b>"+"Designation: "+"</b>" + sharedPreference.getString("designation", "").toString())
        tx5.text = Html.fromHtml("<b>"+"Employee_Id: "+"</b>"+ sharedPreference.getString("e_id", "").toString())
        tx6.text = Html.fromHtml("<b>"+"Mobile: " +"</b>"+ sharedPreference.getString("mobile", "").toString())
        var button: Button = findViewById(R.id.download)
        button.setOnClickListener {
           progressbar=ProgressDialog.show(this, "Loading", "Wait while loading...");
            downloadData()
        }
        seeAll.setOnClickListener {
            val i = Intent(applicationContext, DialogBox::class.java)
            startActivity(i)
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun downloadData() {
        Log.e("Tap", "download")
        val client = ApiClient().getRetrofitClient().create(user::class.java)
        val sharedPreference = getSharedPreferences("userData", Context.MODE_PRIVATE)
        client.tabledata(
            sharedPreference.getString("C_id", "").toString(),
            sharedPreference.getString("L_id", "").toString(),
            sharedPreference.getString("B_id", "").toString(),
            sharedPreference.getString("U_id", "").toString(), "35"
        ).enqueue(object : Callback<GenProduct> {

            override fun onResponse(
                call: Call<GenProduct>,
                response: Response<GenProduct>
            ) {
                Log.d("myresponse", response.toString())
                if (response.isSuccessful) {
                    GlobalScope.launch {
                        database.productdao().deleteallfromtable()
                    }
                    for (i in response.body()!!) {
                        Log.e("table name", i.tname)
                        if (i.tname.equals("GEN_ProductMaster", false)) {
                            Log.e("inner table name", i.tname)
                            var rows: Int = i.li_rows.size
                            for (j in 0 until rows) {
                                GlobalScope.launch {
                                    database.productdao().insertdata(
                                        dbEntity(
                                            i.li_rows[j].get(0),
                                            i.li_rows[j].get(1),
                                            i.li_rows[j].get(2),
                                            i.li_rows[j].get(3),
                                            i.li_rows[j].get(4),
                                            i.li_rows[j].get(5),
                                            i.li_rows[j].get(6),
                                            i.li_rows[j].get(7),
                                            i.li_rows[j].get(8),
                                            i.li_rows[j].get(9),
                                            i.li_rows[j].get(10),
                                            i.li_rows[j].get(11),
                                            i.li_rows[j].get(12),
                                            i.li_rows[j].get(13),
                                            i.li_rows[j].get(14),
                                            i.li_rows[j].get(15),
                                            i.li_rows[j].get(16),
                                            i.li_rows[j].get(17),
                                            i.li_rows[j].get(18),
                                            i.li_rows[j].get(19),
                                            i.li_rows[j].get(20),
                                            i.li_rows[j].get(21),
                                            i.li_rows[j].get(22),
                                            i.li_rows[j].get(23),
                                            i.li_rows[j].get(24),
                                            i.li_rows[j].get(25),
                                            i.li_rows[j].get(26),
                                            i.li_rows[j].get(27),
                                            i.li_rows[j].get(28),
                                            i.li_rows[j].get(29),
                                            i.li_rows[j].get(30),
                                            i.li_rows[j].get(31),
                                            i.li_rows[j].get(32),
                                            i.li_rows[j].get(33),
                                            i.li_rows[j].get(34),
                                            i.li_rows[j].get(35),
                                            i.li_rows[j].get(36),
                                            i.li_rows[j].get(37),
                                            i.li_rows[j].get(38)
                                        )
                                    )
                                }
                            }
                        }
                    }
                    seeAll.visibility = View.VISIBLE
                    progressbar.dismiss()

                }
            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected exception
             * occurred creating the request or processing the response.
             */
            override fun onFailure(call: Call<GenProduct>, t: Throwable) {
            }
        })
    }
}

