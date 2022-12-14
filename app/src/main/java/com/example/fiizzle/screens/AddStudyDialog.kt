package com.example.fiizzle.screens

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.fiizzle.data.PtoJDatabase
import com.example.fiizzle.data.dataClass.PageArray
import com.example.fiizzle.data.entity.Subject
import com.example.fiizzle.databinding.DialogAddStudyBinding
import com.example.fiizzle.utils.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddStudyDialog(
    context : Context,
    private val okCallback : (Boolean) -> Unit
) : Dialog(context) {

    private lateinit var binding : DialogAddStudyBinding
    lateinit var db : PtoJDatabase

    private var spinnerArray = ArrayList<String>()

    private var newSpinnerItem = ""

    private var subject : Subject = Subject()

    private var studyTitle : String = ""
    private var endDate : Long = System.currentTimeMillis()
    private var pageArray : ArrayList<PageArray> = ArrayList(21)
    private val storePage = Array<PageArray?>(21) {null}
    private var totalPageString : String = ""
    private val splitArray : ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogAddStudyBinding.inflate(layoutInflater)
        db = PtoJDatabase.getInstance(context)

        setContentView(binding.root)
        initViews()
    }

//    fun noteTime(view: TextView, url: Long) {
//        val now = System.currentTimeMillis()
//        val nowDate = Date(now)
//
//        val localDate = Date(url)
//        val dt = SimpleDateFormat("yy-MM-dd")
//        val tz = TimeZone.getTimeZone("Asia/Seoul")
//        dt.timeZone = tz
//
//        val nowTime = dt.format(nowDate)
//        val localTime = dt.format(localDate)
//
//        val nowTimestamp = dt.parse(nowTime).time
//        val localTimestamp = dt.parse(localTime).time
//
//        val check = (nowTimestamp - localTimestamp)
//        val diff = check / (24 * 60 * 60 * 1000)
//
//        when (diff) {
//            /* .. ?????? .. */
//        }
//    }

    private fun initViews() = with(binding) {
        //???????????? ??????, ??? ?????? ????????? ?????? dialog ?????????
        setCancelable(true)

        //background ????????????
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        //?????? ????????? ?????? ?????? ??????
        dialogAddStudyAddTv.setOnClickListener {
            //??????, ?????????, ??????, ?????? ??????????????? ??????
            //????????? ????????? ???????????? ??????
            //???????????? ????????? ????????? ???
            if (dialogAddStudyContentTitleEt.text.toString().isNullOrBlank()) {
                Toast.makeText(context, "???????????? ??????????????????.", Toast.LENGTH_SHORT).show()
            }
            else if ( dialogAddStudyContentEndYearEt.text.toString().isNullOrBlank() ||
                    dialogAddStudyContentEndMonthEt.text.toString().isNullOrBlank() ||
                    dialogAddStudyContentEndDateEt.text.toString().isNullOrBlank() ||
                    dialogAddStudyContentEndYearEt.text.toString().toInt() < 2022 ||
                    dialogAddStudyContentEndMonthEt.text.toString().toInt() > 12 ||
                    dialogAddStudyContentEndMonthEt.text.toString().toInt() < 1 ||
                    dialogAddStudyContentEndDateEt.text.toString().toInt() > 31 ||
                    dialogAddStudyContentEndDateEt.text.toString().toInt() < 1) {
                Toast.makeText(context, "???????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show()
            }
            else if (
                dialogAddStudyContentPageStartEt.text.toString().isNullOrBlank() ||
                dialogAddStudyContentPageEndEt.text.toString().isNullOrBlank() ||
                dialogAddStudyContentPageStartEt.text.toString().toInt() >= dialogAddStudyContentPageEndEt.text.toString().toInt()) {
                Toast.makeText(context, "????????? ?????????????????? ????????? ??????????????????.", Toast.LENGTH_SHORT).show()
            }
            else {
                storeSubject(
                    dialogAddStudyContentTitleEt.text.toString(),
                    dialogAddStudyContentEndYearEt.text.toString(),
                    dialogAddStudyContentEndMonthEt.text.toString(),
                    dialogAddStudyContentEndDateEt.text.toString(),
                    dialogAddStudyContentPageStartEt.text.toString().toInt(),
                    dialogAddStudyContentPageEndEt.text.toString().toInt()
                )
                putSubjectToDB()

                spinnerArray = getSpinnerArrayPref(context)
                newSpinnerItem = dialogAddStudyContentTitleEt.text.toString()
                putSpinnerArrayPref(context, spinnerArray, newSpinnerItem)

                okCallback(false)
                dismiss()
            }
        }

        dialogAddStudyCancelTv.setOnClickListener {
            okCallback(false)
            dismiss()
        }
    }

    private fun storeSubject(title : String, year : String, month : String, date : String, start : Int, end : Int) {
        subject.name = title
        subject.userId = getUserIdxPref(context)
        subject.endDate = stringToMillis(year + "??? " + month + "??? " + date + "???")

        calculateEachPage(start, end)
        subject.pageList = totalPageString

        Log.d("STORE_SUBJECT", "????????? : " + subject.name + "/??????????????? : " + subject.userId + "/????????? : " + longToString(subject.endDate) + "/?????????????????? : " + subject.pageList)
    }

    private fun putSubjectToDB() {
        var putSubect : Thread = Thread {
            db.subjectDao.insertSubject(subject)

            Log.d("CHECK_ALL_SUBJECT", db.subjectDao.getAllSubject(getUserIdxPref(context)).toString())
        }
        putSubect.start()

        try {
            putSubect.join()
        }catch (e : InterruptedException) {
            e.printStackTrace()
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