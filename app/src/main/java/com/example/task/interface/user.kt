package com.example.task.`interface`

import com.example.task.model.GenProduct
import com.example.task.model.LoginRequestModel
import com.example.task.model.LoginResponseModel
import com.example.task.model.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface user {
    @POST("Login/GetUserListByMobNum")
    fun postdatas(@Body body:HashMap<String,String>): Call<UserModel>

    @POST("login/login")
    fun logindata(@Body body:LoginRequestModel):Call<String>

    @POST("SyncMaster/GetSyncDetail1/companyId/{companyId}/languageId/{languageId}/businessUnitId/{businessUnitId}/userId/{userId}/lastSyncDate/2022-01-01/transactionName/SyncAllTables/synctype/All")
    fun tabledata(@Path("companyId") companyId: String, @Path("languageId") languageId: String, @Path("businessUnitId") businessUnitId: String, @Path("userId") userId: String, @Body body:String): Call<GenProduct>
}