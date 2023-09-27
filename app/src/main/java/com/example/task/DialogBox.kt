package com.example.task

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.apiintegration.adapter.AddToCartAdapter
import com.example.task.Adapter.DisplayProductAdapter
import com.example.task.`interface`.user
import com.example.task.model.CartItemModel
import com.example.task.model.OrderModel
import com.example.task.model.OrderProductModel
import com.example.task.model.dbEntity
import com.example.task.room.ProductItemDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*


class DialogBox : AppCompatActivity(), DisplayProductAdapter.onSingleItemClicked {
    var data = ArrayList<dbEntity>()
    lateinit var mainAdapter: DisplayProductAdapter
    lateinit var cartAdapter: AddToCartAdapter
    lateinit var rv: RecyclerView
    lateinit var CartRv: RecyclerView
    lateinit var noText: TextView
    lateinit var clear: Button
    lateinit var text: TextView
    lateinit var errtext:TextView
    lateinit var buy: Button
    lateinit var save: Button
    lateinit var usernametext: EditText
    lateinit var phone: EditText
    var cartlist = ArrayList<CartItemModel>()
    var grandtotal: Int = 0
    lateinit var database: ProductItemDatabase
    var itemcount = 0
    lateinit var oneTimeID: String
    lateinit var mydialogue: Dialog
    lateinit var nodata:TextView


    //var simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
    //val finaltime=simpleDateFormat.format(System.currentTimeMillis())
    lateinit var createtime: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialogbox)

        database = Room.databaseBuilder(
            applicationContext,
            ProductItemDatabase::class.java, "productDB"
        ).build()
        GlobalScope.launch {
            data = database.productdao().getdata() as ArrayList<dbEntity>
        }
        text = findViewById(R.id.grandtotaltext)
        buy = findViewById(R.id.buybtn)
        CartRv = findViewById(R.id.cartrecycler)
        nodata= findViewById(R.id.nodatafound)
        CartRv.layoutManager = LinearLayoutManager(this)
        text.visibility = View.GONE
        buy.visibility = View.GONE
        CartRv.visibility = View.GONE
        nodata.visibility=View.VISIBLE

        buy.setOnClickListener {
            val userdialog = Dialog(this)
            userdialog.setContentView(R.layout.dialogforuser)
            usernametext = userdialog.findViewById(R.id.usernameid)
            phone = userdialog.findViewById(R.id.phoneno)
            save = userdialog.findViewById(R.id.savedata)
            errtext=userdialog.findViewById(R.id.errortext)
            intent.putExtra("myname", usernametext.text)

            save.setOnClickListener {
                if (usernametext.text.isNotEmpty() && (phone.text.length >= 10 && phone.text.length <= 12) )  {
                    oneTimeID = System.currentTimeMillis().toString()
                    createtime = System.currentTimeMillis().toString()
                    saveProductOrder()
                    saveOrders()
                    errtext.visibility=View.INVISIBLE
                    text.visibility = View.INVISIBLE
                    buy.visibility = View.INVISIBLE
                    cartlist.clear()
                    cartAdapter.notifyDataSetChanged()
                    nodata.visibility=View.VISIBLE
                    userdialog.dismiss()
                    Log.e("cartlist", cartlist.clear().toString())
                } else {
                   errtext.visibility=View.VISIBLE

                    /* val intent = Intent(this@DialogBox, DialogBox::class.java)
                     startActivity(intent)
     */
                }
            }
            userdialog.setCancelable(true)
            userdialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            userdialog.show()
        }

    }

    private fun saveProductOrder() {
        var productrow: Int = cartlist.size
        /*   GlobalScope.launch {
               database.oproductdao().deletealltheproducts()
           }*/

        for (product in 0 until productrow) {
            GlobalScope.launch {
                database.oproductdao().insertproductorder(
                    OrderProductModel(
                        0,
                        oneTimeID,
                        cartlist[product].dbEntity.ProductName,
                        cartlist[product].dbEntity.SellingRate,
                        cartlist[product].quantity,
                        cartlist[product].dbEntity.ProductSKUId

                    )
                )
            }
        }
    }

    private fun saveOrders() {
         /* GlobalScope.launch {
              database.orderdao().deletealltheOrders()
          }*/
        GlobalScope.launch {
            database.orderdao().insertorder(
                OrderModel(
                    oneTimeID,
                    itemcount.toString(),
                    grandtotal,
                    usernametext.text.toString(),
                    phone.text.toString(),
                    createtime
                )
            )
        }
    }

    /*Log.e("name",newone)*/
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.searchmenu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                Toast.makeText(this, "About Selected", Toast.LENGTH_SHORT).show()
                mydialogue = Dialog(this)
                mydialogue.setContentView(R.layout.customdialog)
                rv = mydialogue.findViewById<RecyclerView>(R.id.recycler)
                noText = mydialogue.findViewById(R.id.no)
                clear = mydialogue.findViewById(R.id.clearbutton)
                allAdapterData(data)
                val editText = mydialogue.findViewById<EditText>(R.id.dialogueid)
                editText.doOnTextChanged { text, _, _, _ ->
                    val query = text.toString().lowercase(Locale.getDefault())
                    filterWithQuery(query)
                }
                clear.setOnClickListener {
                    editText.setText("")
                    rv.visibility = View.VISIBLE
                    noText.visibility = View.INVISIBLE
                }
                rv.layoutManager = LinearLayoutManager(this)
                mydialogue.setCancelable(true)
                mydialogue.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                mydialogue.show()
            }
            R.id.list -> {
                val intent = Intent(this@DialogBox, ListOfProductData::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun allAdapterData(funData: ArrayList<dbEntity>) {
        mainAdapter = DisplayProductAdapter(funData, this)
        rv.adapter = mainAdapter
    }

    private fun filterWithQuery(query: String) {
        if (query.isNotEmpty()) {
            val filteredList: ArrayList<dbEntity> = onFilterChanged(query)
            allAdapterData(filteredList)
            toggleRecyclerView(filteredList)
        } else if (query.isEmpty()) {
            allAdapterData(data)
        }
    }

    private fun toggleRecyclerView(filledData: ArrayList<dbEntity>) {
        if (filledData.isEmpty()) {
            rv.visibility = View.INVISIBLE
            noText.visibility = View.VISIBLE
        } else {
            rv.visibility = View.VISIBLE
            noText.visibility = View.INVISIBLE
        }
    }

    private fun onFilterChanged(filterQuery: String): ArrayList<dbEntity> {
        val filteredList = ArrayList<dbEntity>()
        for (current in data) {
            if (current.ProductName.lowercase(Locale.getDefault()).contains(filterQuery)) {
                filteredList.add(current)
            }
        }
        return filteredList
    }

    override fun onSelected(singleProduct: dbEntity) {
        if (singleProduct.SellingRate.isNotEmpty()) {
            var position = isDataExist(singleProduct)
            if (position == -1) {
                var addingProduct = CartItemModel(1, singleProduct)
                cartlist.add(addingProduct)
            } else {
                var obj = cartlist[isDataExist(singleProduct)]
                obj.quantity = obj.quantity + 1
                cartlist.set(position, obj)
            }
            calculate()
            Toast.makeText(applicationContext, "Added", Toast.LENGTH_LONG).show()
            cartAdapter = AddToCartAdapter(cartlist)
            CartRv.adapter = cartAdapter
            text.visibility = View.VISIBLE
            buy.visibility = View.VISIBLE
            CartRv.visibility = View.VISIBLE
            cartAdapter.notifyDataSetChanged()
            nodata.visibility=View.INVISIBLE
        } else {
            Toast.makeText(
                applicationContext,
                "Price is empty, Select another product",
                Toast.LENGTH_LONG
            ).show()
        }
        mydialogue.dismiss()
    }


    fun isDataExist(singleProduct: dbEntity): Int {

        for (i in cartlist) {
            if (singleProduct.ProductId == i.dbEntity.ProductId) {
                return cartlist.indexOf(i)
            }
        }
        return -1
    }

    fun calculate() {

        grandtotal = 0
        for (i in cartlist) {
            var indvidualTotal = i.quantity * i.dbEntity.SellingRate.toInt()
            grandtotal += indvidualTotal
        }
        itemcount = cartlist.sumOf { it.quantity }
        text.text = "Total = " + grandtotal.toString()
    }
}