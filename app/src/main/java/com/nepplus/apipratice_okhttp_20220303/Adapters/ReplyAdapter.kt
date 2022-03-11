package com.nepplus.apipratice_okhttp_20220303.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.nepplus.apipratice_okhttp_20220303.R
import com.nepplus.apipratice_okhttp_20220303.ViewTopDetailActivity
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

//                        무조건 댓글 목록 새로 고침
//                        Adapter 코딩 => 액티비티의 기능 실행

//                        어댑터 객체화시, mContext 변수에 어느 화면에서 사용하는지 대입
//                        mContext : Context 타입. 대입 객체 : ViewTopic액티비티 객체 => 다형성
//                        부모 형태의 변수에 담긴 자식 객체는, 캐스팅을 통해서 원상 복구 가능.
//                        자식에서 만든 별도의 함수들을 다시 사용 가능.

                        (mContext as ViewTopDetailActivity).getTopicDetailFromServer()
                    }
                }
            )

        }

//        싫어요가 눌려도 마찬가지 처리. => 싫어요 API 호출 (기존 함수 활용) + 토론 상세 화면 댓글 목록 새로 고침
        txtHateCount.setOnClickListener {
            ServerUtil.postRequestReplyLikeOrHate(
                mContext,
                data.id,
                false,
                object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObject: JSONObject) {
                        (mContext as ViewTopDetailActivity).getTopicDetailFromServer()
                    }
                }
            )
        }
//        [도전과제] 싫어요가 눌려도 마찬가지 처리. => 싫어요 API 호출 (기존 함수 활용) + 토론 상세화면 댓글 목록 새로고침

//        좋아요가 눌렸는지, 아닌지. 글씨 색상 변경
        if(data.isMyLike){
            txtLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.naver_red))
        }
        else{
            txtLikeCount.setTextColor(ContextCompat.getColor(mContext, R.color.depp_dark_gray))

        }

       return row
    }
}