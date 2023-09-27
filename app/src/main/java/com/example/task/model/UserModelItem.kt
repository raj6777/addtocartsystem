package com.example.task.model

data class UserModelItem(
    val CompanyList: List<Any>,
    val ISMOBOTPRequire: Boolean,
    val IsMultiCompany: Boolean,
    val OTP: String,
    val OTPEveryLogin: Boolean,
    val PreferredLoginMethod: String,
    val PreferredLoginMethodName: String,
    val UserName: String
)