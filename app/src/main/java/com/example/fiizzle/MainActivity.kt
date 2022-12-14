package com.example.fiizzle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil.setContentView
import com.example.fiizzle.databinding.FragmentAllBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: FragmentAllBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentAllBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSpinner()
    }

    private fun initSpinner() {
        val subject = resources.getStringArray(R.array.spinner)

        val subjectAdapter = ArrayAdapter(this, R.layout.item_spinner, subject)
        binding.allSpinner.adapter = subjectAdapter
    }
}