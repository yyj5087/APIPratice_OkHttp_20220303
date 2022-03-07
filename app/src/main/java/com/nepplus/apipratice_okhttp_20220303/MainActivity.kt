package com.nepplus.apipratice_okhttp_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.nepplus.apipratice_okhttp_20220303.Adapters.TopicAdapter
import com.nepplus.apipratice_okhttp_20220303.databinding.ActivityLoginBinding
import com.nepplus.apipratice_okhttp_20220303.databinding.ActivityMainBinding
import com.nepplus.apipratice_okhttp_20220303.datas.TopicData
import com.nepplus.apipratice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BasicActivity() {
    lateinit var binding: ActivityMainBinding
    
//    실제로 서버가 내려주는 주제 목록을 담을 그릇
    val mTopicList = ArrayList<TopicData>()
    lateinit var mAdapter: TopicAdapter
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
//       코드상으로는 먼저 실행시키지만, 완료는 어댑터 연결보다 늦을 수 도 있다.
//        => 목록에 토론 주제 추가 :  어댑터 연결 이후 추가
//        => 리스트뷰 어댑터의 내용물에 변경 : notifyDataSetChanged 실행
        getTopicListFromServer()
        mAdapter = TopicAdapter(mContext,R.layout.topic_list_item,mTopicList)
        binding.topicListView.adapter = mAdapter

    }

    fun getTopicListFromServer(){
        ServerUtil.getRequestMyInfo(mContext, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObject: JSONObject) {
                
//                서버가 주는 토론 주제 목록 파실 => mTopList에 추가하기.
                val dataObj = jsonObject.getJSONObject("data")
                val topicsArr = dataObj.getJSONArray("topics")
                
//                topicsArr 내부를 하나씩 추출 (JSONObject { }) => TopicData()로 변환

//                JSONArraysms for-each 문법 지원X. (차후 : ArrayList의 for-each 활용예정)
//                JAVA : for (int i; i< 배열.length; i++)와 완전히 동일한 문법
                for (i in 0 until topicsArr.length()){

//                    [   ] => {}, {}, {}, ....순서에 맞는 {} 를 변수에 담자.
//                    JSON파싱의 {   } => (JSONArray에게서) JSONObject추출.
                    val topicObj = topicsArr.getJSONObject(i)

                    Log.d("받아낸주제",topicObj.toString())
//                    TopicData 변수 생성 => 멤버변수에, topicObj가 들고있는 값을 대입
                    val topicData = TopicData()
                    topicData.id = topicObj.getInt("id")
                    topicData.title = topicObj.getString("title")
                    topicData.imageURL = topicObj.getString("img_url")
                    topicData.replyCount = topicObj.getInt("reply_count")

//                    완성된 TopicData 객체를 목록에 추가.


                    mTopicList.add(topicData)
                }
//                리스트뷰의 내용물 새로고침 => UI에 내용물 변경 행위
                runOnUiThread {
                    mAdapter.notifyDataSetChanged()
                }

                
            }

        })
    }

}