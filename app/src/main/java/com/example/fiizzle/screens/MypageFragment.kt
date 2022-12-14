package com.example.fiizzle.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fiizzle.R
import com.example.fiizzle.databinding.FragmentAllBinding
import com.example.fiizzle.databinding.FragmentMypageBinding

class MypageFragment : Fragment() {


    private lateinit var binding : FragmentMypageBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentMypageBinding>(inflater, R.layout.fragment_mypage, container, false)

        clickHandler()

        return binding.root
    }



    private fun clickHandler() {
        binding.allBottomStudy.setOnClickListener{
            findNavController().navigate(R.id.action_mypageFragment_to_allFragment)
        }
    }
}