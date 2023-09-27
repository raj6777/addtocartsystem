package com.example.task.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="Orders" )
data class OrderModel(
    @PrimaryKey(autoGenerate = false)
    val orderid: String,
    val totalProductCount:String,
    val totalAmount:Int,
    val username:String,
    val contectno:String,
    val creatAt: String
    )
