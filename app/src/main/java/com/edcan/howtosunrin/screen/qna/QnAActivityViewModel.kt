package com.edcan.howtosunrin.screen.qna

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.edcan.howtosunrin.utill.qna.Question
import com.edcan.howtosunrin.screen.splash.qnaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QnAActivityViewModel : ViewModel() {
    val question = MutableLiveData(Question())

    suspend fun getOneQuestion() : Question{
        val questions = qnaDB.getAllQuestion()

        return questions.random()
    }

    fun question() {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.Main){
                question.value = getOneQuestion()
            }
        }
    }
}