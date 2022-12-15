package com.example.fiizzle.screens

import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Spinner
import com.example.fiizzle.MainActivity
import com.example.fiizzle.databinding.DialogAddStudyBinding
import org.json.JSONArray
import org.json.JSONException

class AddStudyDialog(
    context : Context,
    private val okCallback : (Boolean) -> Unit
) : Dialog(context) {

    private lateinit var binding : DialogAddStudyBinding

    private val SpinnerSPFKey : String = "SPINNER"
    private val SpinnerArrayKey : String = "SPINNER_PREF"
    lateinit var pref : SharedPreferences
    lateinit var edit : SharedPreferences.Editor
    private var spinnerArray = ArrayList<String>()

    private var newSpinnerItem = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogAddStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()

        pref = context.getSharedPreferences(SpinnerSPFKey, Context.MODE_PRIVATE)
        edit = pref.edit()
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
            getSpinnerArrayPref()
            newSpinnerItem = dialogAddStudyContentTitleEt.text.toString()
            putSpinnerArrayPref()
            okCallback(false)
            dismiss()
        }

        dialogAddStudyCancelTv.setOnClickListener {
            okCallback(false)
            dismiss()
        }
    }

    private fun getSpinnerArrayPref() {
        val json = pref.getString(SpinnerArrayKey, null)

        spinnerArray.clear()

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

    private fun putSpinnerArrayPref() {
        var spinnerJson : JSONArray = JSONArray()

        for (i in spinnerArray) {
            spinnerJson.put(i)
        }
        spinnerJson.put(newSpinnerItem)

        edit.putString(SpinnerArrayKey, spinnerJson.toString())
        edit.apply()
    }

}