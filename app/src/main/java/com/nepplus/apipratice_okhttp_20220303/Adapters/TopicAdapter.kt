package com.nepplus.apipratice_okhttp_20220303.Adapters

import android.content.Context
import android.widget.ArrayAdapter
import com.nepplus.apipratice_okhttp_20220303.datas.TopicData

class TopicAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<TopicData>
) : ArrayAdapter<TopicData>(mContext,resId,mList) {
}