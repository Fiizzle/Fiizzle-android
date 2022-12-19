package com.example.fiizzle.utils

import android.widget.TextView
import java.sql.Timestamp
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

val format_dateToString = SimpleDateFormat("yyyy년 MM월 dd일")
val format_stringToDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

fun dateToString(date : Date) : String {
    return format_dateToString.format(date)
}

fun stringToMillis(str : String) : Long {
    var date = format_dateToString.parse(str, ParsePosition(0))
    var currentLong = date.time

    return currentLong
}

fun longToString(long : Long) : String {
    var date = Date(long)

    return format_dateToString.format(date)
}

//fun noteTime(view: TextView, url: Long) {
//    val now = System.currentTimeMillis()
//    val nowDate = Date(now)
//
//    val localDate = Date(url)
//    val dt = SimpleDateFormat("yy-MM-dd")
//    val tz = TimeZone.getTimeZone("Asia/Seoul")
//    dt.timeZone = tz
//
//    val nowTime = dt.format(nowDate)
//    val localTime = dt.format(localDate)
//
//    val nowTimestamp = dt.parse(nowTime).time
//    val localTimestamp = dt.parse(localTime).time
//
//    val check = (nowTimestamp - localTimestamp)
//    val diff = check / (24 * 60 * 60 * 1000)
//
//    when (diff) {
//        /* .. 처리 .. */
//    }
//}