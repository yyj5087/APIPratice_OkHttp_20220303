package com.nepplus.apipratice_okhttp_20220303.datas

import org.json.JSONObject
import java.io.Serializable

class TopicData : Serializable {
    var id = 0 // id는 Int라고 명시
    var title = "" //title은 String이라고 명시.
    var imageURL = "" // 서버 : img_url, 앱 : imageURL 변수명 다른 경우,
    var replyCount = 0

//    하나의 토론주제 : 여러개의 (목록) 선택진영

    val sideList = ArrayList<SideData>()

    companion object{

        //    주제 정보를 담고 있는 JSONObject가 들어오면 > TopicData형태로 변환해주는 함수 => static 메쏘드
        fun getTopicDataFromJson(JsonObj: JSONObject) : TopicData{
//            기본 내용의 TopicData 생성
            val topicData = TopicData()
//            jsonObj 에서 데이터 추출 >멤버변수 대입
            topicData.id = JsonObj.getInt("id")
            topicData.title = JsonObj.getString("title")
            topicData.imageURL = JsonObj.getString("img_url")
            topicData.replyCount = JsonObj.getInt("reply_count")

//            sides라는 JSONArray가 들어있음.
//            => topicData의 하위 정보로, 선택진영 목록으로 저장.
//            JSONArray > ArrayList
            val sidesArr = JsonObj.getJSONArray("sides")
            for (i in 0 until sidesArr.length()){
//                선택 진영 정보를 들고 있는 JSONObject추출
                val sideObj = sidesArr.getJSONObject(i)
//                sideObj도, SideData로 (선택진영) 변환
                val sideData = SideData.getSideDataFromJson(sideObj)

//                topicData 변수의 하위 목록으로 등록
                topicData.sideList.add(sideData)
            }

//            완성된 TopicData 리턴턴
            return topicData
        }
    }
}