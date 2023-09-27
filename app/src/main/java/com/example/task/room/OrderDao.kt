package com.example.task.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.task.model.*

@Dao
interface OrderDao {
    @Insert
    suspend fun insertorder(orderModel: OrderModel)

    @Query("SELECT * FROM Orders")
    fun getdataoforder():List<OrderModel>

    @Query("DELETE FROM Orders")
    suspend fun deletealltheOrders()

}