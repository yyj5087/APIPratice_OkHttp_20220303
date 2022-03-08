package com.nepplus.apipratice_okhttp_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashActivity : BasicActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

    }

    override fun setValues() {

//        2.5초만 지나면 -> 자동로그인을 해도 되는지? -> 상황에 맞는 화면으로 이동.
        
        val myHandler = Handler(Looper.getMainLooper())
        myHandler.postDelayed({
             
//            자동로그인을 해도 되는가?
//            1) 사용자가 자동로그인 의사를 OK 했는지?
//            2) 로그인 시에 받아낸 토큰값이 지금도 유효한지?
         if(){
             
//             둘다 ok라면, 바로 메인화면으로
         }
            else{
//                아니라면, 로그인 화면으로
            }
                              
        },2500)
    }
}