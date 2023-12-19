package com.bangkit.turtlify.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.model.search.SearchResponseItem
import com.bumptech.glide.Glide

class ListSearchAdapter(private val context: Context): ListAdapter<SearchResponseItem, ListSearchAdapter.ListViewHolder>(DIFF_CALLBACK){

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<SearchResponseItem>() {
            override fun areItemsTheSame(oldItem: SearchResponseItem, newItem: SearchResponseItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SearchResponseItem, newItem: SearchResponseItem): Boolean {
                return oldItem == newItem
            }
        }
    }
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {
        val itemCard: CardView = itemView.findViewById(R.id.card_view)
        val itemPhoto: ImageView = itemView.findViewById(R.id.item_photo)
        val tvItemName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvItemLatinName: TextView = itemView.findViewById(R.id.tv_item_latin_name)
        val tvItemStatus: TextView = itemView.findViewById(R.id.tv_item_status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_search_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val turtle = getItem(position)
        holder.tvItemName.text = turtle.namaLokal
        holder.tvItemLatinName.text = turtle.namaLatin
        if(turtle.statusKonversi!!.split(" ").contains("dilindungi")){
            holder.tvItemStatus.text = "dilindungi"
            holder.tvItemStatus.setTextColor(context.getColor(R.color.red_text))
        } else{
            holder.tvItemStatus.text = "tidak dilindungi"
            holder.tvItemStatus.setTextColor(context.getColor(R.color.green_text))
        }
        Glide.with(context).load(
            turtle.image!!.split(",").first())
            .into(holder.itemPhoto)
    }

    interface OnItemClickListener {
        fun onItemClick(user: SearchResponseItem)
    }


}
