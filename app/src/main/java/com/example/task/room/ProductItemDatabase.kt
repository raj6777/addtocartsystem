package com.example.task.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task.model.dbEntity
import com.example.task.model.OrderModel
import com.example.task.model.OrderProductModel


@Database(entities=[dbEntity::class,OrderModel::class,OrderProductModel::class],version=2, exportSchema = false)
//@TypeConverters(Convertor1::class)
abstract class ProductItemDatabase:RoomDatabase() {
     abstract fun productdao():RoomDao
     abstract fun oproductdao():OrderProductDao
     abstract fun orderdao():OrderDao
    companion object{

        private var INSTANCE: ProductItemDatabase?=null

        fun getdatabase(context: Context):ProductItemDatabase{
            if(INSTANCE==null){

            }
            return INSTANCE!!

        }

    }
    /*@Database(entities=[dbEntity::class],version = 4)
    abstract class AppDatabase : RoomDatabase() {

    }*/
}

