package com.nepplus.apipratice_okhttp_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.nepplus.apipratice_okhttp_20220303.databinding.ActivitySignUpBinding
import com.nepplus.apipratice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BasicActivity() {
    lateinit var binding : ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_sign_up)
        setupEvents()
        setValues()

    }
    override fun setupEvents() {
        binding.btnSignUp.setOnClickListener {
            val inputEmail = binding.edtEmail.text.toString()
            val inputPassword = binding.edtPassword.text.toString()
            val inputNickname = binding.edtNickname.text.toString()

            ServerUtil.putRequestSignUp(
                inputEmail,
                inputPassword,
                inputNickname,
                object : ServerUtil.JsonResponseHandler {
                    override fun onResponse(jsonObject: JSONObject) {

//                        회원가입 성공 /실패 분기
                        val code = jsonObject.getInt("code")
                        if(code == 200){

                        }
                        else{
                            val message = jsonObject.getString("message")
                            runOnUiThread {
                                Toast.makeText(mContext, "실패 사유 : ${message}", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }

                }
            )

        }

    }

    override fun setValues() {

    }



}