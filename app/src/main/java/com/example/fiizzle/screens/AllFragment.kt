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
import com.example.fiizzle.data.StudyList
import com.example.fiizzle.databinding.FragmentAllBinding
import com.example.fiizzle.screens.adapter.AllStudyListRVAdapter
import org.json.JSONArray
import org.json.JSONException

class AllFragment : Fragment() {

    private lateinit var binding : FragmentAllBinding
    private val studyListRVAdapter = AllStudyListRVAdapter()
    private var studyList : ArrayList<StudyList> = ArrayList()

    private lateinit var mContext : Context
    private lateinit var mActivity : MainActivity
    private val SpinnerSPFKey : String = "SPINNER"
    private val SpinnerArrayKey : String = "SPINNER_PREF"
    lateinit var pref : SharedPreferences
    lateinit var edit : SharedPreferences.Editor
    private var spinnerArray = ArrayList<String>()

    private var isFabClicked : Boolean = false

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

        pref = mContext.getSharedPreferences(SpinnerSPFKey, MODE_PRIVATE)
        edit = pref.edit()

        clickHandler()

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
                initSpinner()
            }.show()
        }
    }

    private fun initSpinner() {  // 스피너 초기화
        getSpinnerArrayPref()
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
    }

    private fun setAdapter() {
        binding.allStudyListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.allStudyListRv.adapter = studyListRVAdapter

        getTestList()
        studyListRVAdapter.addList(studyList)
    }

    private fun getTestList() {
        studyList.clear()
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

    private fun getSpinnerArrayPref() {
        val json = pref.getString(SpinnerArrayKey, null)

        spinnerArray.clear()
        spinnerArray.add("전체")

        if (json!=null) {
            try {
                var spinnerJson : JSONArray = JSONArray(json)
                for (i in 0 until spinnerJson.length()) {
                    spinnerArray.add(spinnerJson.optString(i))
                }
            } catch (e : JSONException) {
                e.printStackTrace()
            }
        }
    }

}