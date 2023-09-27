package com.example.task.model

import androidx.room.Entity
import androidx.room.PrimaryKey

data class GenProductItem(
val IndexColumns: String,
val IsDropAndCreate: Boolean,
val i_cols:List<LiCol>,
val li_rows: List<List<String>>,
val pkflds: String,
val tname: String
)