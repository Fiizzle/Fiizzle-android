package com.example.fiizzle.screens

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import com.example.fiizzle.databinding.DialogAddStudyBinding

class AddStudyDialog(
    context : Context
) : Dialog(context) {

    private lateinit var binding : DialogAddStudyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogAddStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {
        //뒤로가기 버튼, 빈 화면 터치를 통해 dialog 사라짐
        setCancelable(true)

        //background 투명하게
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //버튼 클릭에 대한 콜백 처리
        dialogAddStudyAddTv.setOnClickListener {
            //이름, 종료일, 분량, 단위 비어있는지 확인
            //그리고 데이터 넘기면서 액션
            //데이터를 서버에 넘겨야 됨

            dismiss()
        }

        dialogAddStudyCancelTv.setOnClickListener {
            dismiss()
        }
    }

}