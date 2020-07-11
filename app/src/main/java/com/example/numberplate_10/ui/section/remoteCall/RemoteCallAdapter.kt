package com.example.numberplate_10.ui.section.remoteCall

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.numberplate_10.R
import com.example.numberplate_10.data.httpObj.RemoteRowData
import kotlinx.android.synthetic.main.item_remote_call_row.view.*

class RemoteCallAdapter(private val listener: OnItemListener, private var remoteRowDataList: ArrayList<RemoteRowData>) : RecyclerView.Adapter<RemoteCallAdapter.RemoteCallViewHolder>() {

    interface OnItemListener {
        fun onClick(num: String)

    }

    class RemoteCallViewHolder(view: View, private val listener: OnItemListener) : RecyclerView.ViewHolder(view), View.OnClickListener {
        override fun onClick(p0: View?) {
            when(p0?.id) {
                R.id.item_remote_call_num0 -> {listener.onClick(itemView.item_remote_call_num0.text.toString())}
                R.id.item_remote_call_num1 -> {listener.onClick(itemView.item_remote_call_num1.text.toString())}
                R.id.item_remote_call_num2 -> {listener.onClick(itemView.item_remote_call_num2.text.toString())}
                R.id.item_remote_call_num3 -> {listener.onClick(itemView.item_remote_call_num3.text.toString())}
                R.id.item_remote_call_num4 -> {listener.onClick(itemView.item_remote_call_num4.text.toString())}

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteCallViewHolder {
        val remoteCallViewHolder = RemoteCallViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_remote_call_row, parent, false)
                , listener
        )

        remoteCallViewHolder.itemView.item_remote_call_num0.setOnClickListener(remoteCallViewHolder)
        remoteCallViewHolder.itemView.item_remote_call_num1.setOnClickListener(remoteCallViewHolder)
        remoteCallViewHolder.itemView.item_remote_call_num2.setOnClickListener(remoteCallViewHolder)
        remoteCallViewHolder.itemView.item_remote_call_num3.setOnClickListener(remoteCallViewHolder)
        remoteCallViewHolder.itemView.item_remote_call_num4.setOnClickListener(remoteCallViewHolder)

        return remoteCallViewHolder
    }

    override fun getItemCount(): Int {
        return remoteRowDataList.size

    }

    override fun onBindViewHolder(holder: RemoteCallViewHolder, position: Int) {
        setRemoteCallRow(holder, remoteRowDataList.get(position))

    }

    private fun setRemoteCallRow(remoteCallViewHolder: RemoteCallViewHolder, remoteRowData: RemoteRowData) {

        val strNum0 = remoteRowData.rowNumList[0]
        remoteCallViewHolder.itemView.item_remote_call_num0.text = strNum0

        if (remoteRowData.rowNumList[1] != "0") {
            val strNum1 = remoteRowData.rowNumList[1]
            remoteCallViewHolder.itemView.item_remote_call_num1.visibility = View.VISIBLE
            remoteCallViewHolder.itemView.item_remote_call_num1.text = strNum1

        } else {
            remoteCallViewHolder.itemView.item_remote_call_num1.visibility = View.INVISIBLE

        }

        if (remoteRowData.rowNumList[2] != "0") {
            val strNum2 = remoteRowData.rowNumList[2]
            remoteCallViewHolder.itemView.item_remote_call_num2.visibility = View.VISIBLE
            remoteCallViewHolder.itemView.item_remote_call_num2.text = strNum2

        } else {
            remoteCallViewHolder.itemView.item_remote_call_num2.visibility = View.INVISIBLE

        }

        if (remoteRowData.rowNumList[3] != "0") {
            val strNum3 = remoteRowData.rowNumList[3]
            remoteCallViewHolder.itemView.item_remote_call_num3.visibility = View.VISIBLE
            remoteCallViewHolder.itemView.item_remote_call_num3.text = strNum3

        } else {
            remoteCallViewHolder.itemView.item_remote_call_num3.visibility = View.INVISIBLE

        }

        if (remoteRowData.rowNumList[4] != "0") {
            val strNum4 = remoteRowData.rowNumList[4]
            remoteCallViewHolder.itemView.item_remote_call_num4.visibility = View.VISIBLE
            remoteCallViewHolder.itemView.item_remote_call_num4.text = strNum4

        } else {
            remoteCallViewHolder.itemView.item_remote_call_num4.visibility = View.INVISIBLE

        }
    }

}