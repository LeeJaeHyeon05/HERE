package com.example.here

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainFestivalAdapter(private val mList: ArrayList<ItemModel>) : RecyclerView.Adapter<MainFestivalAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainFestivalAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.festival_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainFestivalAdapter.ViewHolder, position: Int) {
        val itemModel = mList[position]


        //페스티벌 이미지, 클릭리스너
        holder.festivalImb.setOnClickListener {
        }
        holder.festivalImb.setImageResource(itemModel.festivalImB)

        //페스티벌 텍스트
        holder.festvalText.setText(itemModel.festivalText)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView : View) : RecyclerView.ViewHolder(ItemView) {
        val festivalImb : ImageButton = itemView.findViewById(R.id.festivalImg)
        val festvalText : TextView = itemView.findViewById(R.id.festivalText)
    }


}