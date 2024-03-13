package com.ciutacuclaudia.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.Locale

val dateFormat =
    SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())

val dateDifferenceFormat = SimpleDateFormat(
    "dd 'days,' HH 'hours,' mm 'minutes,' ss 'seconds'",
    Locale.getDefault()
)