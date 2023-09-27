package com.example.task.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName ="GEN_productMaster" )
data class dbEntity(
    @PrimaryKey
    val PKId: String,
    val ProductId:String,
    val ProductTypeId:String,
    val ProductMasterName:String,
    val BrandName:String,
    val GroupName:String,
    val IsRefundable:String,
    val CompanyId:String,
    val ProductSKUId:String,
    val ProductName:String,
    val SellingRate:String,
    val BaseSellingPrice:String,
    val CurrencyId:String,
    val SellingCurrencySymbol:String,
    val SellingCurrencyName:String,
    val ProductCode:String,
    val ProductCategoryId:String,
    val ProductBrandId:String,
    val ProductImage:String,
    val UOMId:String,
    val ProductBarCode:String,
    val ProductSKUCode:String,
    val UOMName:String,
    val UOMCode:String,
    val ProductGroupId:String,
    val ConversionSKU:String,
    val ConversionValue:String,
    val DimensionUOMId:String,
    val DimensionUOMName:String,
    val WeightKG:String,
    val Height:String,
    val Length:String,
    val Width:String,
    val ProductSKUImage:String,
    val IsLoaded:String,
    val TrackingBy:String,
    val ProductMasterNameLang2:String,
    val ProductNameLang2:String,
    val ArabicRemark:String
    )
