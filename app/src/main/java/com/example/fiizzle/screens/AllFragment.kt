package com.example.fiizzle.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.fiizzle.R
import com.example.fiizzle.databinding.FragmentAllBinding

class AllFragment : Fragment() {

    private lateinit var binding : FragmentAllBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAllBinding>(inflater, R.layout.fragment_all, container, false)
        return binding.root

        initSpinner()
    }
    private fun initSpinner() {  // 스피너 초기화
        // 도시 스피너 어뎁터 연결
        val subject = resources.getStringArray(R.array.spinner)  // 도시 목록

        val subjectAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, subject)
        binding.allSpinner.adapter = subjectAdapter
        binding.allSpinner.setSelection(0)
    }
}