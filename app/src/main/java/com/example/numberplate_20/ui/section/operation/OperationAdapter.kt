package com.example.numberplate_20.ui.section.operation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.numberplate_20.R
import kotlinx.android.synthetic.main.item_operation_uncall.view.*

class OperationAdapter(val listener: OperationAdapterListener, var mNumList: MutableList<Int>) : RecyclerView.Adapter<OperationAdapter.OperationUncallNumViewHolder>() {

    interface OperationAdapterListener {
        fun onNumClick(clickedNum: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationUncallNumViewHolder {
        return OperationUncallNumViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_operation_uncall, parent, false))
    }

    override fun getItemCount(): Int {
        return mNumList.size
    }

    override fun onBindViewHolder(holder: OperationUncallNumViewHolder, position: Int) {
        holder.itemView.item_uncall_num.text = mNumList[position].toString()
        holder.itemView.setOnClickListener {
            listener.onNumClick(mNumList[position].toString())
            mNumList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    class OperationUncallNumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun setNumArr(numList: MutableList<Int>) {
        this.mNumList = numList
        notifyDataSetChanged()
    }
}