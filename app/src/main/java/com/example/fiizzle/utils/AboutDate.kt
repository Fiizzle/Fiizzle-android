package com.example.fiizzle.utils

import java.text.SimpleDateFormat
import java.util.*

val format_dateToString = SimpleDateFormat("yyyy년 MM월 dd일")
val format_stringToDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

fun dateToString(date : Date) : String {
    return format_dateToString.format(date)
}

//fun stringToDate(str : String) : Date {
//    return format_stringToDate.parse(str)
//}