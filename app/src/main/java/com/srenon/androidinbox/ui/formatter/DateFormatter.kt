package com.srenon.androidinbox.ui.formatter

import java.time.format.DateTimeFormatter

val requestFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("E, MMMM d @ H:mm a.")
val timestampFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("H:mma")
