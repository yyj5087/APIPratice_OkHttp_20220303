package com.nepplus.apipratice_okhttp_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.apipratice_okhttp_20220303.databinding.ActivityLoginBinding
import com.nepplus.apipratice_okhttp_20220303.databinding.ActivityMainBinding
import com.nepplus.apipratice_okhttp_20220303.datas.TopicData
import com.nepplus.apipratice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BasicActivity() {
    lateinit var binding: ActivityMainBinding
    
//    실제로 서버가 내려주는 주제 목록을 담을 그릇
    val mTopicList = ArrayList<TopicData>()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setupEvents()
        setValues()

    }
    override fun setupEvents() {

    }

    override fun setValues() {

//      메인 화면 정보 가져오기 => API 호출 / 응답 처리
        getTopicListFromServer()

    }

    fun getTopicListFromServer(){
        ServerUtil.getRequestMyInfo(mContext, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObject: JSONObject) {
                
//                서버가 주는 토론 주제 목록 파실 => mTopList에 추가하기.
                val dataObj = jsonObject.getJSONObject("data")
                val topicsArr = dataObj.getJSONArray("topics")
                
//                topicsArr 내부를 하나씩 추출 (JSONObject { }) => TopicData()로 변환
                
            }

        })
    }

}