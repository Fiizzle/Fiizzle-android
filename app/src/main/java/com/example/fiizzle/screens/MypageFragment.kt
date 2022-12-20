package com.example.fiizzle.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fiizzle.R
import com.example.fiizzle.data.PtoJDatabase
import com.example.fiizzle.data.dataClass.MypageSubjectArray
import com.example.fiizzle.data.entity.Subject
import com.example.fiizzle.data.entity.User
import com.example.fiizzle.databinding.FragmentAllBinding
import com.example.fiizzle.databinding.FragmentMypageBinding
import com.example.fiizzle.utils.getUserIdxPref
import java.text.SimpleDateFormat
import java.util.*

class MypageFragment : Fragment() {


    private lateinit var binding : FragmentMypageBinding
    lateinit var db : PtoJDatabase

    private var userIdx = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentMypageBinding>(inflater, R.layout.fragment_mypage, container, false)
        userIdx = getUserIdxPref(requireContext())
        db = PtoJDatabase.getInstance(requireContext())

        getUserMypageInfo()
        clickHandler()

        return binding.root
    }

    private fun getUserMypageInfo(){
        Thread{
            val subjects = db.subjectDao.getAllSubject(userIdx)
            val user = db.userDao.getUserInfo(userIdx)
            val status = countCurrentSubject(subjects)
            binding.mypageEmailTv.text = user.email
            binding.mypageNicknameBigTv.text = user.nickname
            binding.mypageNicknameSmallTv.text = user.nickname
            binding.mypageCurrentCntTv.text = status.current.toString()
            binding.mypageFinishCntTv.text = status.finish.toString()
        }.start()

    }


    private fun countCurrentSubject(subjects: List<Subject>): MypageSubjectArray {
        var finish = 0
        var current = 0
        val now = System.currentTimeMillis()
        val date = Date(now)
        val sdf = SimpleDateFormat("yyyyMMdd")
        val getTime = sdf.format(date)

        for(subject in subjects){
            val getSubjectTime = sdf.format(Date(subject.endDate))
            if(getTime.toInt() <= getSubjectTime.toInt()) current++
            else finish++

        }

        return MypageSubjectArray(current, finish)
    }

    private fun clickHandler() {
        binding.allBottomStudy.setOnClickListener{
            findNavController().navigate(R.id.action_mypageFragment_to_allFragment)
        }
    }
}