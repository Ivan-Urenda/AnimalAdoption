package com.urenda.animaladoption.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.urenda.animaladoption.R

class MessageAdapter(private val user: String): RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private var messages: List<Message> = emptyList()

    fun setData(list: List<Message>){
        messages = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_message,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messages[position]

        val myMessageLayout = holder.itemView.findViewById<ConstraintLayout>(R.id.myMessageLayout)
        val otherMessageLayout = holder.itemView.findViewById<ConstraintLayout>(R.id.otherMessageLayout)
        val myMessageTextView = holder.itemView.findViewById<TextView>(R.id.myMessageTextView)
        val othersMessageTextView = holder.itemView.findViewById<TextView>(R.id.othersMessageTextView)


        if(user == message.from){
            myMessageLayout.visibility = View.VISIBLE
            otherMessageLayout.visibility = View.GONE

            myMessageTextView.text = message.message
        } else {
            myMessageLayout.visibility = View.GONE
            otherMessageLayout.visibility = View.VISIBLE

            othersMessageTextView.text = message.message
        }

    }

    override fun getItemCount(): Int {
        return messages.size
    }

    class MessageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}