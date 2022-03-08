package com.nepplus.apipratice_okhttp_20220303.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData : Serializable {
    var id = 0 // id는 Int라고 명시
    var title = "" //title은 String이라고 명시.
    var imageURL = "" // 서버 : img_url, 앱 : imageURL 변수명 다른 경우,
    var replyCount = 0


    companion object{

        //    주제 정보를 담고 있는 JSONObject가 들어오면 > TopicData형태로 변환해주는 함수 => static 메쏘드
        fun getTopicDataFromJson(JsonObj: JSONObject) : TopicData{
//            기본 내용의 TopicData 생성
            val topicData = TopicData()
//            jsonObj 에서 데이터 추출 >멤버변수 대입
            topicData.id = JsonObj.getInt("id")
            topicData.title = JsonObj.getString("title")
            topicData.imageURL = JsonObj.getString("img_url")
            topicData.replyCount = JsonObj.getInt("replyCount")

//            완성된 TopicData 리턴턴
            return topicData
        }
    }
}