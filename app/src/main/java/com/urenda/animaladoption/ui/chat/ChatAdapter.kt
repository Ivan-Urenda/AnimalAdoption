package com.urenda.animaladoption.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.urenda.animaladoption.R

class ChatAdapter(val chatClick: (Chat) -> Unit): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {
    var chats: List<Chat> = emptyList()

    fun setData(list: List<Chat>){
        chats = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_chat,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

        val chatNameText = holder.itemView.findViewById<TextView>(R.id.chatNameText)
        val usersTextView = holder.itemView.findViewById<TextView>(R.id.usersTextView)

        chatNameText.text = chats[position].name
        usersTextView.text = chats[position].users.toString()

        holder.itemView.setOnClickListener {
            chatClick(chats[position])
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}