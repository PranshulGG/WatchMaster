package com.pranshulgg.watchmaster.core.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

val DEFAULT_DATE_FORMATTER: DateTimeFormatter =
    DateTimeFormatter.ofPattern("MMM dd, yyyy")

val SYSTEM_ZONE: ZoneId = ZoneId.systemDefault()

fun Instant.formatDate(): String =
    atZone(SYSTEM_ZONE).format(DEFAULT_DATE_FORMATTER)
