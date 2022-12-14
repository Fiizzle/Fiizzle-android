package com.example.fiizzle.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fiizzle.R
import com.example.fiizzle.data.StudyList
import com.example.fiizzle.databinding.FragmentAllBinding
import com.example.fiizzle.screens.adapter.AllStudyListRVAdapter

class AllFragment : Fragment() {

    private lateinit var binding : FragmentAllBinding
    private val studyListRVAdapter = AllStudyListRVAdapter()
    private var studyList : ArrayList<StudyList> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAllBinding>(inflater, R.layout.fragment_all, container, false)

//        binding.button.setOnClickListener {
//            AddStudyDialog(requireContext()).show()
//        }

        return binding.root

//        initSpinner()
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
    }

    private fun initSpinner() {  // 스피너 초기화
        // 도시 스피너 어뎁터 연결
        val subject = resources.getStringArray(R.array.spinner)  // 도시 목록

        val subjectAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, subject)
        binding.allSpinner.adapter = subjectAdapter
        binding.allSpinner.setSelection(0)
    }

    private fun setAdapter() {
        binding.allStudyListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.allStudyListRv.adapter = studyListRVAdapter

        getTestList()
        studyListRVAdapter.addList(studyList)


    }

    private fun getTestList() {
        studyList.add(
            StudyList(
                "list01",
                "2022년 12월 15일",
                7
            )
        )

        studyList.add(
            StudyList(
                "list02",
                "2022년 12월 16일",
                8
            )
        )

        studyList.add(
            StudyList(
                "list03",
                "2022년 12월 19일",
                16
            )
        )

        studyList.add(
            StudyList(
                "list04",
                "2022년 12월 21일",
                10
            )
        )
    }

}