package com.pranshulgg.watchmaster.data.local.converters

import androidx.room.TypeConverter

class SeasonNameConverter {

    @TypeConverter
    fun fromSet(value: Set<String>?): String? =
        value?.joinToString(",")

    @TypeConverter
    fun toSet(value: String?): Set<String>? =
        value?.split(",")?.map { it }?.toSet()
}
