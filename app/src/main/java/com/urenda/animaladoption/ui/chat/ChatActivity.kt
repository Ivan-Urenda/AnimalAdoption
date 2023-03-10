package com.urenda.animaladoption.ui.chat

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.urenda.animaladoption.R

class ChatActivity : AppCompatActivity() {

    private var chatId = ""
    private var user = ""

    private var db = Firebase.firestore

    private lateinit var messagesRecylerView: RecyclerView
    private lateinit var sendMessageButton: Button
    private lateinit var messageTextField: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messagesRecylerView = findViewById(R.id.messagesRecylerView)
        sendMessageButton = findViewById(R.id.sendMessageButton)
        messageTextField = findViewById(R.id.messageTextField)

        intent.getStringExtra("chatId")?.let { chatId = it }
        intent.getStringExtra("user")?.let { user = it }

        if(chatId.isNotEmpty() && user.isNotEmpty()) {
            initViews()
        }

        if (resources.configuration.locale.language.equals("es"))
        {
            this.setTitle("Chat")
        }
        else
        {
            this.setTitle("Chat")
        }
    }

    private fun initViews(){
        messagesRecylerView.layoutManager = LinearLayoutManager(this)
        messagesRecylerView.adapter = MessageAdapter(user)

        sendMessageButton.setOnClickListener { sendMessage() }

        val chatRef = db.collection("chats").document(chatId)

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { messages ->
                val listMessages = messages.toObjects(Message::class.java)
                (messagesRecylerView.adapter as MessageAdapter).setData(listMessages)
            }

        chatRef.collection("messages").orderBy("dob", Query.Direction.ASCENDING)
            .addSnapshotListener { messages, error ->
                if(error == null){
                    messages?.let {
                        val listMessages = it.toObjects(Message::class.java)
                        (messagesRecylerView.adapter as MessageAdapter).setData(listMessages)
                    }
                }
            }
    }

    private fun sendMessage(){
        val message = Message(
            message = messageTextField.text.toString(),
            from = user
        )

        db.collection("chats").document(chatId).collection("messages").document().set(message)

        messageTextField.setText("")


    }

}