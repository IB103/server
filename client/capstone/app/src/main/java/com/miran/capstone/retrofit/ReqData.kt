package com.miran.capstone.retrofit

import com.google.gson.annotations.SerializedName

data class ReqLogin(
    //@SerializedName("userID")
   // val userID: String,

    //@SerializedName("userPW")
    //val userPW: String,

    @SerializedName("password1")
    val password1: String,

    @SerializedName("username")
    val username: String
)