package com.example.task.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.task.model.OrderAndOrderProductModel
import com.example.task.model.OrderProductModel

@Dao
interface OrderProductDao {
    @Insert
    suspend fun insertproductorder(orderProductModel: OrderProductModel)

    @Query("DELETE FROM Product_Order")
    suspend fun deletealltheproducts()

    @Query("SELECT * FROM Product_Order INNER JOIN Orders ON Product_Order.orderid=Orders.orderid")
    fun fetchparticulardata():List<OrderAndOrderProductModel>

}