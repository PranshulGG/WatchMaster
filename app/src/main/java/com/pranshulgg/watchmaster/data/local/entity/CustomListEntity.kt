package com.pranshulgg.watchmaster.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pranshulgg.watchmaster.core.model.MediaListsIcons

@Entity(tableName = "custom_lists")
data class CustomListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,
    val description: String = "",

    val icon: MediaListsIcons? = null,

    val ids: List<Long>,

    val isPinned: Boolean = false
)