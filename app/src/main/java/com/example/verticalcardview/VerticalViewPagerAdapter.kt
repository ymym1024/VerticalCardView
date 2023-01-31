package com.example.verticalcardview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class VerticalViewPagerAdapter(var list : ArrayList<Int>) : RecyclerView.Adapter<VerticalViewPagerAdapter.PagerViewHolder>(){
    inner class PagerViewHolder(parent : ViewGroup): RecyclerView.ViewHolder
        (LayoutInflater.from(parent.context).inflate(R.layout.card_item_view, parent, false)) {
        val card_image = itemView.findViewById<ImageView>(R.id.iv_card_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder = PagerViewHolder(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.card_image.setImageResource(list[position])
    }
}