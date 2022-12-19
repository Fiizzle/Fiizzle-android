package com.example.fiizzle.screens

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.fiizzle.data.dataClass.PageArray
import com.example.fiizzle.databinding.DialogAddStudyBinding
import com.example.fiizzle.utils.dateToString
import com.example.fiizzle.utils.getSpinnerArrayPref
import com.example.fiizzle.utils.putSpinnerArrayPref
import com.example.fiizzle.utils.splitTotalPageWithDay
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddStudyDialog(
    context : Context,
    private val okCallback : (Boolean) -> Unit
) : Dialog(context) {

    private lateinit var binding : DialogAddStudyBinding

    private var spinnerArray = ArrayList<String>()

    private var newSpinnerItem = ""

    private var studyTitle : String = ""
    private var endDate : Date = Date()
    private var pageArray : ArrayList<PageArray> = ArrayList(21)
    private val storePage = Array<PageArray?>(21) {null}
    private var totalPageString : String = ""
    private val splitArray : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogAddStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    fun noteTime(view: TextView, url: Long) {
        val now = System.currentTimeMillis()
        val nowDate = Date(now)

        val localDate = Date(url)
        val dt = SimpleDateFormat("yy-MM-dd")
        val tz = TimeZone.getTimeZone("Asia/Seoul")
        dt.timeZone = tz

        val nowTime = dt.format(nowDate)
        val localTime = dt.format(localDate)

        val nowTimestamp = dt.parse(nowTime).time
        val localTimestamp = dt.parse(localTime).time

        val check = (nowTimestamp - localTimestamp)
        val diff = check / (24 * 60 * 60 * 1000)

        when (diff) {
            /* .. 처리 .. */
        }
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
            if (dialogAddStudyContentTitleEt.text.toString().isNullOrBlank()) {
                Toast.makeText(context, "학습명을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if ( dialogAddStudyContentEndYearEt.text.toString().isNullOrBlank() ||
                    dialogAddStudyContentEndMonthEt.text.toString().isNullOrBlank() ||
                    dialogAddStudyContentEndDateEt.text.toString().isNullOrBlank() ||
                    dialogAddStudyContentEndYearEt.text.toString().toInt() < 2022 ||
                    dialogAddStudyContentEndMonthEt.text.toString().toInt() > 12 ||
                    dialogAddStudyContentEndMonthEt.text.toString().toInt() < 1 ||
                    dialogAddStudyContentEndDateEt.text.toString().toInt() > 31 ||
                    dialogAddStudyContentEndDateEt.text.toString().toInt() < 1) {
                Toast.makeText(context, "종료일을 정확히 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else if (
                dialogAddStudyContentPageStartEt.text.toString().isNullOrBlank() ||
                dialogAddStudyContentPageEndEt.text.toString().isNullOrBlank() ||
                dialogAddStudyContentPageStartEt.text.toString().toInt() >= dialogAddStudyContentPageEndEt.text.toString().toInt()) {
                Toast.makeText(context, "시작과 종료페이지를 정확히 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else {

                spinnerArray = getSpinnerArrayPref(context)
                newSpinnerItem = dialogAddStudyContentTitleEt.text.toString()
                putSpinnerArrayPref(context, spinnerArray, newSpinnerItem)

                Log.d("ENDDATE_ORIGIN", endDate.toString())
                studyTitle = newSpinnerItem
                endDate = Date(
                    dialogAddStudyContentEndYearEt.text.toString().toInt() - 1900,
                    dialogAddStudyContentEndMonthEt.text.toString().toInt() - 1,
                    dialogAddStudyContentEndDateEt.text.toString().toInt()
                )
                Log.d("STUDYTITLE", studyTitle)
                Log.d("ENDDATE", endDate.toString())
                Log.d("ENDDATE_DateToString", dateToString(endDate))
                calculateEachPage(
                    dialogAddStudyContentPageStartEt.text.toString().toInt(),
                    dialogAddStudyContentPageEndEt.text.toString().toInt()
                )
                okCallback(false)
                dismiss()
            }
        }

        dialogAddStudyCancelTv.setOnClickListener {
            okCallback(false)
            dismiss()
        }
    }

    private fun calculateEachPage(start : Int, end : Int) {

        pageArray.clear()

        val pageAmount : Int = end - start + 1
        var now = start

        if (pageAmount < 21) {
            val mid = 21 - pageAmount
            for (i in 0 until mid) {
                storePage[i] = PageArray(0,0)
            }
            for (i in mid until 21) {
                storePage[i] = PageArray(now, now)
                now += 1
            }
        }
        else {
            val interval : Int = pageAmount / 21
            val extra = pageAmount % 21
            if (extra == 0) {
                for (i in 0 until 21) {
                    storePage[i] = PageArray(now, now + interval - 1)
                    now += interval
                }
            }
            else {
                for (i in 0 until extra) {
                    storePage[i] = PageArray(now, now + interval)
                    now += interval + 1
                }
                for (i in extra until 21) {
                    storePage[i] = PageArray(now, now + interval - 1)
                    now += interval
                }
            }
        }

//        var idx = 0
//        for (j in storePage) {
//            Log.d("STOREPAGE", "index : " + idx.toString() + ", startPage : " + j!!.startPage.toString() + ", endPage : " + j!!.endPage.toString())
//            idx++
//        }

        setTotalPageString()

    }

    private fun setTotalPageString() {
        totalPageString = ""
        for (i in storePage) {
            totalPageString += i!!.startPage.toString() + "," + i!!.endPage.toString() + "/"
        }

        Log.d("TOTAL_PAGESTRING", totalPageString)

        splitTotalPageWithDay(3, totalPageString)
        splitTotalPageWithDay(10, totalPageString)
    }
}