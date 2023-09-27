package com.example.task.convertot

import androidx.room.TypeConverter
import com.example.task.model.GenProductItem
import com.example.task.model.LiCol
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type



    /*   @TypeConverter
    fun toListOfStrings(flatStringList: String): List<String> {
        return flatStringList.split(",")
    }
    @TypeConverter
    fun fromListOfStrings(listOfString: List<LiCol>): String {
        return listOfString.joinToString(",")
    }
    }*/
    class Convertor1 {
        @TypeConverter
        fun toListOfStrings(flatStringList: List<LiCol>): List<LiCol> {
            return flatStringList
        }

        @TypeConverter
        fun fromListOfStrings(listOfString: List<LiCol>): String {
            return listOfString.joinToString(",")
        }
    }

