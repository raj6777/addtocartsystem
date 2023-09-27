package com.example.task.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.task.model.dbEntity
import com.example.task.room.ProductItemDatabase

class ProductViewModel (application: Application):ViewModel(){
    private  val dbEntity:ProductItemDatabase=ProductItemDatabase.getdatabase(application)
    internal val alldata:LiveData<List<dbEntity>> = dbEntity.productdao().getdata() as LiveData<List<dbEntity>>
}