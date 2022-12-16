package com.example.fiizzle.utils

import android.content.Context
import org.json.JSONArray
import org.json.JSONException


val SPF_NAME_SPINNER : String = "SPINNER"
val SPF_SPINNER_ARRAY : String = "SPINNER_PREF"

fun getSpinnerArrayPref(context: Context) : ArrayList<String> {
    val pref = context.getSharedPreferences(SPF_NAME_SPINNER, Context.MODE_PRIVATE)
    val edit = pref.edit()

    val json = pref.getString(SPF_SPINNER_ARRAY, null)
    val spinnerArray = ArrayList<String>()
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

    return spinnerArray
}

fun putSpinnerArrayPref(context : Context, spinnerArray : ArrayList<String>, newSpinnerItem : String) {
    var spinnerJson : JSONArray = JSONArray()

    for (i in spinnerArray) {
        spinnerJson.put(i)
    }
    spinnerJson.put(newSpinnerItem)

    val pref = context.getSharedPreferences(SPF_NAME_SPINNER, Context.MODE_PRIVATE)
    val edit = pref.edit()

    edit.putString(SPF_SPINNER_ARRAY, spinnerJson.toString())
    edit.apply()
}