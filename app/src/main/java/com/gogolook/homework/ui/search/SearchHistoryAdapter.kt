package com.gogolook.homework.ui.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gogolook.homework.R
import kotlinx.android.synthetic.main.item_history.view.*

class SearchHistoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: ArrayList<String> = ArrayList()
    var listener: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        holder.itemView.tv_history.text = item
        holder.itemView.tv_history.setOnClickListener {
            it.tag = holder.itemView.tv_history.text
            listener?.onClick(it)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    fun updateData(data: List<String>) {
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(listener: View.OnClickListener) {
        this.listener = listener
    }

    fun isDataEmpty() = this.data.size == 0
}