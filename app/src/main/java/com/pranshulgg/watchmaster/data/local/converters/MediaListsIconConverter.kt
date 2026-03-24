package com.pranshulgg.watchmaster.data.local.converters

import androidx.room.TypeConverter
import com.pranshulgg.watchmaster.core.model.MediaListsIcons


class MediaListsIconConverter {

    @TypeConverter
    fun fromListsIcons(listsIcons: MediaListsIcons): String = listsIcons.name

    @TypeConverter
    fun toListsIcons(value: String): MediaListsIcons =
        MediaListsIcons.valueOf(value)
}
