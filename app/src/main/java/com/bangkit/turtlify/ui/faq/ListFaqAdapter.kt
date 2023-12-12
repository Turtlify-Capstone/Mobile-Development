package com.bangkit.turtlify.ui.faq

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.turtlify.R
import com.bangkit.turtlify.data.model.FaqItem

class ListFaqAdapter(private val faqList: List<FaqItem>) : RecyclerView.Adapter<ListFaqAdapter.FaqViewHolder>() {
    class FaqViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvQuestion: TextView = itemView.findViewById(R.id.tvQuestion)
        val tvAnswer: TextView = itemView.findViewById(R.id.tvAnswer)
        val ivExpandCollapse: ImageView = itemView.findViewById(R.id.ivExpandCollapse)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_faq, parent, false)
        return FaqViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        val currentFAQ = faqList[position]

        holder.tvQuestion.text = currentFAQ.question
        holder.tvQuestion.setOnClickListener {
            toggleAnswerVisibility(holder)
        }
        holder.tvAnswer.text = currentFAQ.answer
        holder.ivExpandCollapse.setOnClickListener {
            toggleAnswerVisibility(holder)
        }
    }

    override fun getItemCount() = faqList.size

    private fun toggleAnswerVisibility(holder: FaqViewHolder) {
        if (holder.tvAnswer.visibility == View.VISIBLE) {
            holder.tvAnswer.visibility = View.GONE
            holder.ivExpandCollapse.setImageResource(R.drawable.ic_expand_more)
        } else {
            holder.tvAnswer.visibility = View.VISIBLE
            holder.ivExpandCollapse.setImageResource(R.drawable.ic_expand_less)
        }
    }
}