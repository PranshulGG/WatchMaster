package com.pranshulgg.watchmaster.data.local.converters

import androidx.room.TypeConverter

class LongListConverter {

    @TypeConverter
    fun fromList(list: List<Long>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String?): List<Long>? {
        return value
            ?.split(",")
            ?.mapNotNull { it.toLongOrNull() }
    }
}
