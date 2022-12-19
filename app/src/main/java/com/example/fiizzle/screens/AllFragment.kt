package com.example.fiizzle.screens

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fiizzle.MainActivity
import com.example.fiizzle.R
import com.example.fiizzle.data.PtoJDatabase
import com.example.fiizzle.data.dataClass.StudyList
import com.example.fiizzle.data.entity.Subject
import com.example.fiizzle.databinding.FragmentAllBinding
import com.example.fiizzle.screens.adapter.AllStudyListRVAdapter
import com.example.fiizzle.utils.*

class AllFragment : Fragment() {

    private lateinit var binding : FragmentAllBinding
    lateinit var db : PtoJDatabase

    private val studyListRVAdapter = AllStudyListRVAdapter()
    private var studyList : ArrayList<StudyList> = ArrayList()

    private lateinit var mContext : Context
    private lateinit var mActivity : MainActivity
    private var spinnerArray = ArrayList<String>()

    private var isFabClicked : Boolean = false
    private var userIdx : Int = 0

    private lateinit var subjectList : ArrayList<Subject>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mContext = context
            mActivity = activity as MainActivity
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAllBinding>(inflater, R.layout.fragment_all, container, false)
        db = PtoJDatabase.getInstance(mContext)

        clickHandler()
        userIdx = getUserIdxPref(mContext)
        Log.d("USER_IDX_ALL", userIdx.toString())

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setAdapter()
        initSpinner()
        Log.d("ONRESUME","resume")
    }
    private fun clickHandler() {
        binding.allBottomMypage.setOnClickListener{
            findNavController().navigate(R.id.action_allFragment_to_mypageFragment)
        }

        binding.allSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 != 0) {
                    val action = AllFragmentDirections.actionAllFragmentToBookFragment(p2)
                    findNavController().navigate(action)

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

//        binding.allFab.setOnClickListener {
//            AddStudyDialog(requireContext()) {
//                newSpinnerItem = it
//            }.show()
//        }
        binding.allFab.setOnClickListener {
            isFabClicked = true
            AddStudyDialog(requireContext()) {
                isFabClicked = it

                getStudyList()
                studyListRVAdapter.addList(studyList)
                studyListRVAdapter.notifyDataSetChanged()
                Log.d("AFTER_FAB", studyList.toString())

                initSpinner()
            }.show()
        }
    }

    private fun initSpinner() {  // 스피너 초기화
        spinnerArray = getSpinnerArrayPref(mContext)
        spinnerArray.add(0, "전체")
        val subject = spinnerArray.toArray()

        val subjectAdapter = ArrayAdapter(requireContext(), R.layout.item_spinner, subject)
        binding.allSpinner.adapter = subjectAdapter
        binding.allSpinner.setSelection(0)

        if (spinnerArray.size > 1) {
            binding.allNothingTv.visibility = View.GONE
            binding.allStudyListRv.visibility = View.VISIBLE
        } else {
            binding.allNothingTv.visibility = View.VISIBLE
            binding.allStudyListRv.visibility = View.GONE
        }
        Log.d("AFTER_INIT_SPINNER", spinnerArray.toString())
    }

    private fun setAdapter() {
        binding.allStudyListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.allStudyListRv.adapter = studyListRVAdapter

        getStudyList()
        studyListRVAdapter.addList(studyList)

        Log.d("SET_ADAPTER", studyList.toString())
    }

    private fun getStudyList() {
        studyList.clear()

        var getSubjectList : Thread = Thread {
            subjectList = db.subjectDao.getAllSubject(userIdx) as ArrayList<Subject>
        }
        getSubjectList.start()

        try {
            getSubjectList.join()
        } catch (e : InterruptedException) {
            e.printStackTrace()
        }

        for (i in subjectList) {
            studyList.add(subjectToStudyList(i))
        }
        Log.d("GET_STUDYLIST", studyList.toString())
    }

    private fun subjectToStudyList(subject : Subject) : StudyList {
        var study : StudyList = StudyList()
        study.title = subject.name
        study.endDate = longToString(subject.endDate)
        study.d_day = cal_d_day(subject.endDate) + 1
        if (study.d_day == 1) {
            if (sameDays(subject.endDate)) {
                study.d_day = 0
            }
        }
        else if (study.d_day <= 0) {
            study.d_day = 0
        }

        return study
    }
}