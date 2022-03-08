package com.nepplus.apipratice_okhttp_20220303.datas

import org.json.JSONObject

class ReplyData(
    var id: Int,
    var content: String,
) {

    var whiter = UserData() //모든 댓글에는 작성자가 있다. null 가능성이 없다
//    보조 생성자 추가 연습 : 파라미터 X.
    constructor() : this(0,"내용없음")
    companion object{
        fun getReplyDataFromJson(jsonObj: JSONObject) : ReplyData{
            val replyData = ReplyData()
//            JSON정보 > 멤버변수 채우기기

            replyData.id = jsonObj.getInt("id")
            replyData.content = jsonObj.getString("content")
            replyData.whiter = UserData.getUserDataFromJson(jsonObj.getJSONObject("user"))
            return replyData
        }
    }
}