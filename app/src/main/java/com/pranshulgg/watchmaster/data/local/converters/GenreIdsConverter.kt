package com.pranshulgg.watchmaster.data.local.converters

import androidx.room.TypeConverter

class GenreIdsConverter {

    @TypeConverter
    fun fromList(list: List<Int>?): String? {
        return list?.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String?): List<Int>? {
        return value
            ?.split(",")
            ?.mapNotNull { it.toIntOrNull() }
    }
}
