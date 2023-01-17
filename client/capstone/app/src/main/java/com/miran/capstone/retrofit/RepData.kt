package com.miran.capstone.retrofit

import com.google.gson.annotations.SerializedName

data class RepLogin(
    @SerializedName("Code")
    val Code: String,

    @SerializedName("Msg")
    val Msg: String
)