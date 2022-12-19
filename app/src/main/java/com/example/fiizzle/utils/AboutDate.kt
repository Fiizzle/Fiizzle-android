package com.example.fiizzle.utils

import android.util.Log
import android.widget.TextView
import java.sql.Timestamp
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

val format_dateToString = SimpleDateFormat("yyyy년 MM월 dd일")

fun stringToMillis(str : String) : Long {
    var date = format_dateToString.parse(str, ParsePosition(0))
    var currentLong = date.time

    return currentLong
}

fun longToString(long : Long) : String {
    var date = Date(long)

    return format_dateToString.format(date)
}

fun cal_d_day(date : Long) : Int {
    var calDate = date - System.currentTimeMillis()
    var calDateDays = calDate / (24*60*60*1000)
    Log.d("CAL_DATEDAYS", calDateDays.toString())

    return calDateDays.toInt()
}