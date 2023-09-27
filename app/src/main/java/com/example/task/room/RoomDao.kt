package com.example.task.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.task.model.*

@Dao
interface RoomDao {
    @Insert
    suspend fun insertdata(dbEntity: dbEntity)


    @Query("DELETE FROM GEN_productMaster")
    suspend fun deleteallfromtable()


    @Query("SELECT * FROM GEN_productMaster")
    fun getdata():List<dbEntity>
}