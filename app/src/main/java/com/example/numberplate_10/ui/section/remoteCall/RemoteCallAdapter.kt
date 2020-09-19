package com.example.numberplate_10.ui.section.remoteCall

import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.numberplate_10.R
import kotlinx.android.synthetic.main.item_remote_call_row.view.*

class RemoteCallAdapter(private val listener: OnItemListener, private var remoteRowDataList: ArrayList<String>) : RecyclerView.Adapter<RemoteCallAdapter.RemoteCallViewHolder>() {

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

    private fun setRemoteCallRow(remoteCallViewHolder: RemoteCallViewHolder, remoteRowData: String) {
        val remoteRowDataSplit = remoteRowData.split("*")

        val remoteRowDataViewSet = setOf<TextView>(remoteCallViewHolder.itemView.item_remote_call_num0,
                remoteCallViewHolder.itemView.item_remote_call_num1,
                remoteCallViewHolder.itemView.item_remote_call_num2,
                remoteCallViewHolder.itemView.item_remote_call_num3,
                remoteCallViewHolder.itemView.item_remote_call_num4)

        (remoteRowDataSplit.indices).forEach {
            val strNum = remoteRowDataSplit[it]
            remoteRowDataViewSet.elementAt(it).apply {
                if (strNum != "0") {
                    this.visibility = VISIBLE
                    this.text = strNum

                } else {
                    this.visibility = INVISIBLE

                }
            }
        }
    }

}