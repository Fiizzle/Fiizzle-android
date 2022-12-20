package com.example.fiizzle.utils

import android.util.Log
import com.example.fiizzle.data.dataClass.PageArray

fun splitTotalPageWithDay(day : Int, totalPage : String) : PageArray {
    val splitArray = totalPage.split("/")
    val arr = splitArray[day-1].split(",")
    val dayPage = PageArray(arr[0].toInt(), arr[1].toInt())

    Log.d("SPLIT_ARRAY_TOTAL", splitArray.toString())
    Log.d("SPLIT_ARRAY_DAY", arr.toString())
    Log.d("SPLIT_TEST", "day : " + day + ", start : " + dayPage.startPage + ", end : " + dayPage.endPage)

    return dayPage
}
