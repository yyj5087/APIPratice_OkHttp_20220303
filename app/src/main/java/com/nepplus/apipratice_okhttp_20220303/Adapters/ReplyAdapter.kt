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
import org.w3c.dom.Text

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
        var row = tempRow!!



        return row
    }
}