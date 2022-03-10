package com.nepplus.apipratice_okhttp_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.nepplus.apipratice_okhttp_20220303.databinding.ActivityEditReplyBinding
import com.nepplus.apipratice_okhttp_20220303.datas.TopicData

class EditReplyActivity : BasicActivity() {
    lateinit var binding : ActivityEditReplyBinding
//
    lateinit var mTopicData: TopicData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_edit_reply)
        mTopicData = intent.getSerializableExtra("topic") as TopicData
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

    }

    override fun setValues() {

        binding.txtTopicTitle.text = mTopicData.title
        binding.txtSideTitle.text = mTopicData.mySelectedSide!!.title
    }


}