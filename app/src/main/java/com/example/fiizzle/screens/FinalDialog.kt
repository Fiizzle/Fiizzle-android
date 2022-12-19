package com.example.fiizzle.screens

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.fiizzle.databinding.DialogFinalBinding

class FinalDialog (
    context : Context
    ) : Dialog(context) {
        private lateinit var binding : DialogFinalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogFinalBinding.inflate(layoutInflater)

        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {
        setCancelable(true)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogFinalCloseTv.setOnClickListener {
            dismiss()
        }
    }
}