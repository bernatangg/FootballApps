package com.result.isoftscore.util

import android.content.res.Resources
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun String.dateFormatter(inputFormat: String = "yyyy-MM-dd",
                         outputFormat: String = "E, MMM dd yyyy"): String {
    val dateFormat = SimpleDateFormat(inputFormat, Locale.ENGLISH)
    val date = dateFormat.parse(this)
    val returnFormat = SimpleDateFormat(outputFormat, Locale.ENGLISH)
    return returnFormat.format(date)
}

fun String.timeFormatter(inputFormat: String = "HH:mm:ss",
                         outputFormat: String = "HH:mm"): String {
    val timeFormat = SimpleDateFormat(inputFormat, Locale.ENGLISH)
    val time = timeFormat.parse(this)
    val returnFormat = SimpleDateFormat(outputFormat, Locale.ENGLISH)
    return returnFormat.format(time)
}

fun String.dateTimeToMillis(format: String = "yyyy-MM-dd HH:mm:ss"): Long {
    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    val date = formatter.parse(this)
    return date.time
}

fun String.splitAndJoin(splitSeparator: String, joinSeparator: String): String{
    val list: List<String> = this.split(splitSeparator).map { it.trim()}
    val output = list.joinToString(joinSeparator)
    return output
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun String?.nullToEmpty(): String = this ?: ""

fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()