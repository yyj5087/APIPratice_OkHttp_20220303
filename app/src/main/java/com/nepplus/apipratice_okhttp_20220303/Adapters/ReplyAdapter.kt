package com.nepplus.apipratice_okhttp_20220303.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.nepplus.apipratice_okhttp_20220303.R
import com.nepplus.apipratice_okhttp_20220303.datas.ReplyData
import com.nepplus.apipratice_okhttp_20220303.datas.TopicData
import com.nepplus.apipratice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*

class ReplyAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<ReplyData>
) : ArrayAdapter<ReplyData>(mContext,resId,mList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if(tempRow == null){
            tempRow = LayoutInflater.from(mContext).inflate(R.layout.reply_list_item, null)

        }
        val row = tempRow!!

        val data = mList[position]

        val txtSelectSide = row.findViewById<TextView>(R.id.txtSelectSide)
        val txtWriterNickname = row.findViewById<TextView>(R.id.txtWriterNickname)
        val txtReplyContent = row.findViewById<TextView>(R.id.txtReplyContent)
        val txtCreateAt = row.findViewById<TextView>(R.id.txtCreateAt)

        val txtReReplyContent = row.findViewById<TextView>(R.id.txtReReplyContent)
        val txtLikeCount = row.findViewById<TextView>(R.id.txtLikeCount)
        val txtHateCount = row.findViewById<TextView>(R.id.txtHateCount)

        txtReplyContent.text = data.content
        txtWriterNickname.text = data.whiter.nickname
        txtSelectSide.text = "${data.selectedSide.title}"

//        임시  -  작성일자만 "2022-03-10" 형태로 표현 => 연, 월, 일 데이터로 가공공
//        월은 1작게 나옴 +1로 보정
//        txtCreateAt.text = "${data.createdAt.get(Calendar.YEAR)}-${data.createdAt.get(Calendar.MONTH) + 1}-${data.createdAt.get(Calendar.DAY_OF_MONTH)}"

//        임시2 - "2022-03-10" 형태로 표현 => SImpleDataFormat 활용

//        연습.
//        양식 1) 2022년 3월 5일
//        양식 2) 220305
//        양식 3) 3월 5일 오전 2시 5분
//        양식 4) 21년 3/5 (토)- 18:05
        val sdf1 =SimpleDateFormat("yyyy-MM-dd")
        val sdf2 =SimpleDateFormat("yyyy-MM-dd")
        val sdf3 =SimpleDateFormat("yyyy-MM-dd")
        val sdf4 =SimpleDateFormat("yyyy-MM-dd")


//        val sdf =SimpleDateFormat("yyyy-MM-dd")
//        val sdf =SimpleDateFormat("yyyy년M월 d일")
////        sdf.format(Data객체) => 지정해둔 양식의 String으로 가공
////        createdAt : Calendar/ format의 피라미터: Data -> calendar 내용물인 time 변수가 Data
//        txtCreateAt.text = sdf.format(data.createdAt.time)
        txtCreateAt.text = data.getFormattedCreatedAt()

        txtReReplyContent.text = "답글 ${data.reReplyCount}"
        txtLikeCount.text = "좋아요 ${data.likeCount}"
        txtHateCount.text = "싫어요 ${data.hateCount}"

        txtLikeCount.setOnClickListener {

//            서버에 이 댓글에 좋아요 알림.
            ServerUtil.postRequestReplyLikeOrHate(
                mContext,
                data.id,
                true,
                object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObject: JSONObject) {

                    }
                }
            )

        }

        return row
    }
}