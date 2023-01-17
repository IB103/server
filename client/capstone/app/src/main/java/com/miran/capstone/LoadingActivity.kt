package com.miran.capstone

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class LoadingActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        var delayTime:Long = 1000

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        Handler().postDelayed(Runnable(){
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, delayTime)

    }
}