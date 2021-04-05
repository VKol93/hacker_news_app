package com.onramp.android.takehome.ui.utils

import java.text.SimpleDateFormat
import java.util.*

fun longToDateTime(long: Long): String{
    val date = Date(long*1000)
    return SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.US).format(date)

}