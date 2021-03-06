package com.gogolook.homework.ui.search

import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.gogolook.homework.R
import com.gogolook.homework.model.api.model.response.ImageInfo
import kotlinx.android.synthetic.main.item_image.view.*

class SearchAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: ArrayList<ImageInfo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]


        holder.itemView.apply {
            val placeholder = ColorDrawable(ContextCompat.getColor(context, R.color.gray2))
            val requestOptions = RequestOptions()
            requestOptions.placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(placeholder)
                .transform(CenterCrop())
                .transform(RoundedCorners(16))

            Glide.with(context)
                .load(item.previewURL)
                .apply(requestOptions)
                .centerCrop()
                .into(iv_image)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}

    fun updateData(data: List<ImageInfo>,page:Int) {
        this.data.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    fun isDataEmpty() = this.data.size == 0
}