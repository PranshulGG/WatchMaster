package com.pranshulgg.watchmaster.data.local.converters

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.TypeConverter
import java.time.Instant

class InstantConverters {

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromInstant(value: Instant?): Long? =
        value?.toEpochMilli()

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toInstant(value: Long?): Instant? =
        value?.let { Instant.ofEpochMilli(it) }
}
