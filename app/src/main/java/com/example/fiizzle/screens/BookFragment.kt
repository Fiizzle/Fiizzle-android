package com.example.fiizzle.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.fiizzle.R
import com.example.fiizzle.databinding.FragmentBookBinding

class BookFragment : Fragment() {


    private lateinit var binding : FragmentBookBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentBookBinding>(inflater, R.layout.fragment_book, container, false)


        return binding.root
    }
}