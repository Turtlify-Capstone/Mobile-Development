package com.bangkit.turtlify.ui.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.database.entity.Turtle
import com.bumptech.glide.Glide

class HistoryAdapter(private val context: Context, private val listHistory: List<Turtle>) : RecyclerView.Adapter<HistoryAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_histories, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (namaLokal, _, image, _, namaLatin, _, _, _, _, status) = listHistory[position]
        holder.tvItemName.text = namaLokal!!.split(",").first()
        holder.tvItemLatinName.text = namaLatin
        if(status!!.split(" ").contains("dilindungi")){
            holder.tvItemStatus.text = "dilindungi"
            holder.tvItemStatus.setTextColor(context.getColor(R.color.red_text))
        } else{
            holder.tvItemStatus.text = "tidak dilindungi"
            holder.tvItemStatus.setTextColor(context.getColor(R.color.green_text))
        }
        Glide.with(context).load(
            image!!.split(",").first()
        ).into(holder.itemPhoto)
        val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        if (position == listHistory.size -1) {
                params.bottomMargin = 80
        } else {
            params.bottomMargin = 12
        }
        holder.itemCard.setOnClickListener { onItemClickCallback.onItemClicked(listHistory[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listHistory.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemCard: CardView = itemView.findViewById(R.id.card_view)
        val itemPhoto: ImageView = itemView.findViewById(R.id.item_photo)
        val tvItemName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvItemLatinName: TextView = itemView.findViewById(R.id.tv_item_latin_name)
        val tvItemStatus: TextView = itemView.findViewById(R.id.tv_item_status)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Turtle)
    }
}