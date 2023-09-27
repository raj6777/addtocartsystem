package com.example.task.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Product_Order")
data class OrderProductModel(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val orderid:String,
    val productname:String,
    val productsellingrate:String,
    val productqty:Int,
    val productskuid:String
    )
