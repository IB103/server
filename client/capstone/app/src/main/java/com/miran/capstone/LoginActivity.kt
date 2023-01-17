package com.miran.capstone

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.miran.capstone.retrofit.RepLogin
import com.miran.capstone.retrofit.ReqLogin
import com.miran.capstone.retrofit.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginbt: TextView =findViewById(R.id.loginbt)
        val register_bt: TextView =findViewById(R.id.register)
        val idtext:TextView=findViewById(R.id.idtext)
        val pwtext:TextView=findViewById(R.id.pwtext)
        loginbt.setOnClickListener(){
            val id=idtext.getText().toString()
            val pw=pwtext.getText().toString()
            Log.d("ID",id )
            Log.d("Pw",pw )
            //////////////////////////////
            var server_info = "223.194.133.220:8080" //username password1 password2 email
            var retrofit = Retrofit.Builder().baseUrl("http://$server_info/")
                .addConverterFactory(GsonConverterFactory.create()).build()
            var service = retrofit.create(RetrofitService::class.java)
            var postReqLogin = ReqLogin(id,pw)
            service.login(postReqLogin).enqueue(object : Callback<RepLogin> {
                @SuppressLint("Range")
                override fun onResponse(call: Call<RepLogin>, response: Response<RepLogin>) {
                    if(response.isSuccessful) {
                        var result: RepLogin? = response.body()
                        if(result?.Code.equals("9000")){
                            Log.d("INFO", "성공: " + result?.toString())
//                            startMainActivity()
                        }else{
                            Log.d("ERR", "실패: " + result?.toString())
                        }
                    }else{
                        // 통신이 실패한 경우(응답코드 3xx, 4xx 등)
                        Log.d("ERR", "onResponse 실패")
                    }
                }

                override fun onFailure(call: Call<RepLogin>, t: Throwable) {
                    // 통신 실패 (인터넷 끊킴, 예외 발생 등 시스템적인 이유)
                    Log.d("ERR", "onFailure 에러: " + t.message.toString())
                }
            })

            /////////////////////////////
        }

        register_bt.setOnClickListener(){
            startActivity(Intent(applicationContext,RegisterActivity::class.java))
        }
    }

}