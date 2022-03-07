package com.nepplus.apipratice_okhttp_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.apipratice_okhttp_20220303.databinding.ActivityLoginBinding
import com.nepplus.apipratice_okhttp_20220303.databinding.ActivityMainBinding

class MainActivity : BasicActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setupEvents()
        setValues()

    }
    override fun setupEvents() {

    }

    override fun setValues() {

    }


}