package com.edcan.howtosunrin.ui.qnaSlider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.edcan.howtosunrin.R
import com.edcan.howtosunrin.databinding.FragmentQnaBinding
import com.edcan.howtosunrin.utill.qna.Question

class QnAFragment(val question: Question) : Fragment() {
    lateinit var binding : FragmentQnaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_qna, container, false)

        binding.questionData = question

        return binding.root
    }
}