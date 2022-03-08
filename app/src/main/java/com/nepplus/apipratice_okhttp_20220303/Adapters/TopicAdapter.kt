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
import com.nepplus.apipratice_okhttp_20220303.datas.TopicData
import org.w3c.dom.Text

class TopicAdapter(
    val mContext: Context,
    resId: Int,
    val mList: List<TopicData>
) : ArrayAdapter<TopicData>(mContext,resId,mList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView
        if(tempRow == null){
            tempRow = LayoutInflater.from(mContext).inflate(R.layout.topic_list_item, null)

        }
        var row = tempRow!!

        val data = mList[position]
        val txtTile = row.findViewById<TextView>(R.id.txtTitle)
        val imgBackground = row.findViewById<ImageView>(R.id.imgBackground)
        txtTile.text = data.title
//        data > 서버에서 준 주제 데이터
//        imageURL 변수 파싱 => 이미지의 인터넷 주소.
//        웹에 있는 이미지 >이미지뷰에 적용 > Glide 라이브러리

        Glide.with(mContext).load(data.imageURL).into(imgBackground)

        return row
    }
}