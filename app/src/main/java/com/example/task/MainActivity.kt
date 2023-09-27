package com.example.task

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.task.`interface`.user
import com.example.task.model.LoginRequestModel
import com.example.task.model.LoginResponseModel
import com.example.task.model.UserModel
import com.example.task.network.ApiClient
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    val userArr = ArrayList<String>()
    lateinit var spin: Spinner
    lateinit var edittext: EditText
    lateinit var progressbar: ProgressDialog
    lateinit var validationtxt: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        spin = findViewById(R.id.spinner)
        edittext = findViewById(R.id.textfield)
        validationtxt = findViewById(R.id.validation)
        spin.visibility = View.INVISIBLE
        val loginbtn: Button = findViewById(R.id.Loginid)
        loginbtn.setOnClickListener {
            postUserData()
        }
        spin.onItemSelectedListener = this
    }

    fun postUserData() {
        val body = HashMap<String, String>()
        if (edittext.text.isNotEmpty() && (edittext.text.length >= 10 && edittext.text.length <= 12)) {

            validationtxt.visibility = View.INVISIBLE
            progressbar = ProgressDialog.show(this, "Loading", "Wait while loading...");
            body.put("mobNum", edittext.text.toString())
            val client = ApiClient().getRetrofitClient().create(user::class.java)
            client.postdatas(body).enqueue(object : Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    if (response.isSuccessful) {
                        for (i in response.body()!!) {
                            userArr.add(i.UserName)
                        }
                        val aa = ArrayAdapter(
                            applicationContext,
                            android.R.layout.simple_spinner_item,
                            userArr
                        )
                        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        spin.setAdapter(aa)
                        spin.visibility = View.VISIBLE

                        progressbar.dismiss()
                    }
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Log.e("err", t.toString())
                }
            })
        } else {
            validationtxt.visibility = View.VISIBLE
        }
    }

        fun loginUserData(usrname: String) {
            val myModel = LoginRequestModel(
                "mob", "", "", "", "123456", "", "1234",
                edittext.text.toString(), "", "", "", usrname, "", "1234", "" +
                        "", "", "", ""
            )
            val client = ApiClient().getRetrofitClient().create(user::class.java)
            client.logindata(myModel).enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>,
                    response: Response<String>
                ) {
                    Log.e("res", response.toString())
                    if (response.isSuccessful) {
                        val newString = response.body()
                        val gson = Gson()
                        val model = gson.fromJson(newString, LoginResponseModel::class.java)
                        val sharedPreference =
                            getSharedPreferences("userData", Context.MODE_PRIVATE)
                        var editor = sharedPreference.edit()
                        editor.putString("username", model.LoggedInUserName)
                        editor.putString("first", model.FirstName)
                        editor.putString("last", model.LastName)
                        editor.putString("designation", model.DesignationName)
                        editor.putString("e_id", model.EmployeeId.toString())
                        editor.putString("mobile", model.OKDollarMobile.toString())
                        editor.putString("B_id", model.BusinessUnitId)
                        editor.putString("L_id", model.PreferredLanguageId.toString())
                        editor.putString("U_id", model.LoggedInUserId.toString())
                        editor.putString("C_id", model.CompanyId.toString())
                        editor.apply()

                        Log.e("login", model.toString())
                        val i = Intent(applicationContext, DisplayUserData::class.java)
                        startActivity(i)
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("err-login", t.toString())
                }

            })
        }

        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            loginUserData(userArr[p2])
            Log.e("se", userArr[p2])
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            Log.e("notselected", "ytt")
        }

    }
