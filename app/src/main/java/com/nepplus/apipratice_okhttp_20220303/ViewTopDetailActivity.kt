package com.nepplus.apipratice_okhttp_20220303

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.nepplus.apipratice_okhttp_20220303.Adapters.ReplyAdapter
import com.nepplus.apipratice_okhttp_20220303.databinding.ActivityViewTopDetailBinding
import com.nepplus.apipratice_okhttp_20220303.datas.ReplyData
import com.nepplus.apipratice_okhttp_20220303.datas.TopicData
import com.nepplus.apipratice_okhttp_20220303.utils.ServerUtil
import org.json.JSONObject

class ViewTopDetailActivity : BasicActivity() {
    lateinit var binding: ActivityViewTopDetailBinding

//    보여주게 될 토론 주제 데이터 > 이벤트처리, 데이터 표현 등 여러 함수에서 사용
    lateinit var mTopicData: TopicData
    lateinit var mAdapter: ReplyAdapter
    val mReplyList = ArrayList<ReplyData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_view_top_detail)
        mTopicData = intent.getSerializableExtra("topic") as TopicData

        setupEvents()
        setValues()

    }
    override fun setupEvents() {

        binding.btnPostReply.setOnClickListener {

//            투표를 하지 않은 상태라면, 댓글 작성도 불가.
            if(mTopicData.mySelectedSide == null){
                Toast.makeText(mContext, "의견을 개진할 진영을 선택하셔야 합니다.", Toast.LENGTH_SHORT).show()
//                클릭 이벤트 자체를 강제 종료 (Intent 실행을 막자)
                return@setOnClickListener //return: 함수의 결과를 지정 => 그 뒤의 코드는 실행 x = 함수 강제 종료
            }
            val myIntent = Intent(mContext,EditReplyActivity::class.java)
            myIntent.putExtra("topic",mTopicData)
            startActivity(myIntent)
        }





//        btn 클릭 => 첫 진영의 id값을 찾아서, 거기에 투표
//            서버에 전달 => API 활용
        binding.btnVote1.setOnClickListener {
//            서버의 투표 API 호출
//            투표 현황 새로고침 (응답)
            ServerUtil.postRequestVote(mContext, mTopicData.sideList[0].id,object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObject: JSONObject) {

//                    토스트로, 서버가 알려준 현재 상황 (신규투표, OR 재투표 OR 취소등)
                    val message = jsonObject.getString("message")
                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
//                    변경된 득표 현황을 다시 불려오자
                    getTopicDetailFromServer()
                }

            })


        }
//        투표현황
        binding.btnVote2.setOnClickListener {
//            2번진영 선택시, 그 진영에 투표하기
            ServerUtil.postRequestVote(mContext,mTopicData.sideList[1].id, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObject: JSONObject) {
                    val message = jsonObject.getString("message")
                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                    getTopicDetailFromServer()
                }

            })
        }
    }

    override fun setValues() {

        mAdapter = ReplyAdapter(mContext,R.layout.reply_list_item,mReplyList)
        binding.replyListView.adapter = mAdapter
        setTopicDataToUi()


//        어차피 onResume에서 서버에 연결 예정.
//        getTopicDetailFromServer()
    }
    fun setTopicDataToUi(){

//        토론 주제에 대한 데이터들을, UI에 반영하는 함수.
//        화면 초기 진입 실행 + 서버에서 다시 받아왔을때도 실행
        binding.txtTitle.text = mTopicData.title
        Glide.with(mContext).load(mTopicData.imageURL).into(binding.imgTopicBackground)

//        1번진영 제목, 2번진영 제목
        binding.txtSide1.text = mTopicData.sideList[0].title
        binding.txtSide2.text = mTopicData.sideList[1].title

//        1번진영 득표수, 2번진영 득표수
        binding.txtVoteCount1.text = "${mTopicData.sideList[0].voteCount}표"
        binding.txtVoteCount1.text = "${mTopicData.sideList[1].voteCount}표"

//        내가 선택한 진영이 있을때, (투표를 해놨을때)
//        이미 투표한 진영은 문구를 변경하자 (투표취소) (enabled=false )
        if(mTopicData.mySelectedSide!=null){
//            첫번째 진영을 투표했느지?
//            두번째 진영을 투표했는지?
            if(mTopicData.mySelectedSide!!.id == mTopicData.sideList[0].id){
//                첫 진영에 투표한 경우
                binding.btnVote1.text = "투표 취소"
                binding.btnVote2.text = "다시 투표"
            }
            else{
//                두변째 진영에 투표
                binding.btnVote1.text = "다시 투표"
                binding.btnVote2.text = "투표 취소"
            }
        }
        else{
//            아무데도 투표하지 않는 경우
            binding.btnVote1.text = "투표 하기"
            binding.btnVote2.text = "투표 하기"
        }
    }

    fun getTopicDetailFromServer(){
        ServerUtil.getRequestTopicDetail(mContext,mTopicData.id, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObject: JSONObject) {

                val dataObj = jsonObject.getJSONObject("data")
                val topicObj = dataObj.getJSONObject("topic")

//                토론 정보 JSONObject (topicObj) => TopicData() 형태로 변환 (여러 화면에서 진행. 함수로 만들어두자)
                val topicData = TopicData.getTopicDataFromJson(topicObj)
//                변환된 객체를, mTopicData로 다시 대입. => UI 반영도 다시 실행.
                mTopicData = topicData

                runOnUiThread {
                    setTopicDataToUi()
                }

//                mReplyList에, 댓글목록이 추가 된다.
//                => 기존에 다른 댓글들이 들어있다면, 그 뒤에 이어서 추가 된다.
//                => 기존 댓글 목록을 전부 삭제하고 나서, 추가하자.
                mReplyList.clear()

                // topicObj 내부에는 replies라는 댓글 목록 JSONArray도 들어있다.
//                mReplyList에 넣어주자
                val repliesArr = topicObj.getJSONArray("replies")
                for (i in 0 until repliesArr.length()){
                    val replyObj = repliesArr.getJSONObject(i)

                    mReplyList.add(ReplyData.getReplyDataFromJson(replyObj))
                }
//                서버의 동작이므로, 어댑터 세팅보다 늦게 끝날수 있다. (notifydatasetchanged)
                runOnUiThread {
                    mAdapter.notifyDataSetChanged()
                }

            }

        })
    }
// 이 화면에 들어올때 마다, 댓글 목록 새로고침.
    override fun onResume() {
        super.onResume()
    getTopicDetailFromServer()

    }

}