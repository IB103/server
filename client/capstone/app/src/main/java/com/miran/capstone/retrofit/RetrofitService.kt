package com.miran.capstone.retrofit

import retrofit2.Call
import retrofit2.http.*


interface RetrofitService {

    // Login
    @Headers("accept: application/json", "content-type: application/json")
    @POST("login/test")
    fun login(
        @Body reqLogin: ReqLogin
    ): Call<RepLogin>
}