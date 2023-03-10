package com.urenda.animaladoption.ui.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.urenda.animaladoption.R
import java.util.*

class ListOfChatsActivity : AppCompatActivity() {
    private var user = ""

    private var db = Firebase.firestore

    private lateinit var newChatButton: Button
    private lateinit var listChatsRecyclerView: RecyclerView
    private lateinit var newChatText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_chats)

        intent.getStringExtra("user")?.let { user = it }

        newChatButton = findViewById(R.id.newChatButton)
        listChatsRecyclerView = findViewById(R.id.listChatsRecyclerView)
        newChatText = findViewById(R.id.newChatText)

        if (user.isNotEmpty()){
            initViews()
        }

        if (resources.configuration.locale.language.equals("es"))
        {
            this.setTitle("Lista de chats")
        }
        else
        {
            this.setTitle("Chat List")
        }
    }

    private fun initViews(){
        newChatButton.setOnClickListener { newChat() }

        listChatsRecyclerView.layoutManager = LinearLayoutManager(this)
        listChatsRecyclerView.adapter =
            ChatAdapter { chat ->
                chatSelected(chat)
            }

        val userRef = db.collection("users").document(user)

        userRef.collection("chats")
            .get()
            .addOnSuccessListener { chats ->
                val listChats = chats.toObjects(Chat::class.java)

                (listChatsRecyclerView.adapter as ChatAdapter).setData(listChats)
            }

        userRef.collection("chats")
            .addSnapshotListener { chats, error ->
                if(error == null){
                    chats?.let {
                        val listChats = it.toObjects(Chat::class.java)

                        (listChatsRecyclerView.adapter as ChatAdapter).setData(listChats)
                    }
                }
            }
    }

    private fun chatSelected(chat: Chat){
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("chatId", chat.id)
        intent.putExtra("user", user)
        startActivity(intent)
    }

    private fun newChat(){
        val chatId = UUID.randomUUID().toString()
        val otherUser = newChatText.text.toString()
        val users = listOf(user, otherUser)

        val chat = Chat(
            id = chatId,
            name = "Chat con $otherUser",
            users = users
        )

        db.collection("chats").document(chatId).set(chat)
        db.collection("users").document(user).collection("chats").document(chatId).set(chat)
        db.collection("users").document(otherUser).collection("chats").document(chatId).set(chat)

        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("chatId", chatId)
        intent.putExtra("user", user)
        startActivity(intent)
    }
}