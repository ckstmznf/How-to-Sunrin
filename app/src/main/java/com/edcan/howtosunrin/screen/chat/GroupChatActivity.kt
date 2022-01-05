package com.edcan.howtosunrin.screen.chat

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.edcan.howtosunrin.R
import com.edcan.howtosunrin.databinding.ActivityGroupChatBinding
import com.edcan.howtosunrin.model.SharedUtil
import com.edcan.howtosunrin.model.chat.Chat
import com.edcan.howtosunrin.model.chat.ChatDB
import com.edcan.howtosunrin.model.chat.ChatUtil
import com.edcan.howtosunrin.screen.chat.recycler.GroupChatRecyclerAdpter
import com.edcan.howtosunrin.screen.splash.chatDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class GroupChatActivity : AppCompatActivity() {
    lateinit var binding : ActivityGroupChatBinding
    lateinit var viewModel: GroupChatActivityViewModel
    lateinit var groupChat_RecyclerAdapter: GroupChatRecyclerAdpter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_chat)
        viewModel = ViewModelProvider(this).get(GroupChatActivityViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val userId = SharedUtil.pref.getString(SharedUtil.keyUserId, "none")

        groupChat_RecyclerAdapter = GroupChatRecyclerAdpter(this)
        binding.recyclerGroupchat.adapter = groupChat_RecyclerAdapter

//        var LastchatData : MutableList<Chat>? = null

//        CoroutineScope(Dispatchers.Main).launch {
//            while(true) {
//                val chatData = viewModel.getChatData()
//                if (LastchatData != chatData) {
//                    groupChat_RecyclerAdapter.data.clear()
//                    LastchatData = chatData
//
//                    for (data in chatData) {
//                        groupChat_RecyclerAdapter.data.add(data)
//                    }
//                    groupChat_RecyclerAdapter.notifyDataSetChanged()
//                }
//            }
//        }

        chatDB.db.collection("ChatGroup")
            .addSnapshotListener { value, error ->
                if(error != null){
                    Toast.makeText(this, "채팅 에러 발생", Toast.LENGTH_LONG).show()
                    finish()

                    return@addSnapshotListener
                }

                if(value == null){
                    Toast.makeText(this, "체칭 값이 없습니다.", Toast.LENGTH_LONG).show()
                    finish()

                    return@addSnapshotListener
                }

                val chatList = mutableListOf<Chat>()

                for(doc in value){
                    chatList.add(doc.toObject(Chat::class.java))
                }

                groupChat_RecyclerAdapter.data = chatList
                groupChat_RecyclerAdapter.notifyDataSetChanged()
            }


        binding.btnGchatSend.setOnClickListener {
            if(viewModel.content.value!!.isEmpty()) {
                Toast.makeText(this, "채팅 내용을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            CoroutineScope(Dispatchers.IO).launch {
                val result = viewModel.sendChat(userId.toString())

                withContext(Dispatchers.Main) {
                    if(result == ChatUtil.ResultSuccess) {
//                        Toast.makeText(applicationContext, "성공적으로 채팅을 보냈습니다!", Toast.LENGTH_SHORT).show()
                        binding.message.text = null
                    } else {
//                        Toast.makeText(applicationContext, "채팅을 보내지 못했습니다.", Toast.LENGTH_SHORT).show()
                        binding.message.text = null
                    }
                }
            }
        }
    }
}