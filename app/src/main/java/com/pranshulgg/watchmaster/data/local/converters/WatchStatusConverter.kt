package com.pranshulgg.watchmaster.data.local.converters

import androidx.room.TypeConverter
import com.pranshulgg.watchmaster.model.WatchStatus

class WatchStatusConverter {

    @TypeConverter
    fun fromStatus(status: WatchStatus): String = status.name

    @TypeConverter
    fun toStatus(value: String): WatchStatus =
        WatchStatus.valueOf(value)
}
