package com.pranshulgg.watchmaster.data.repository

import com.pranshulgg.watchmaster.core.model.MediaListsIcons
import com.pranshulgg.watchmaster.data.local.dao.CustomListsDao
import com.pranshulgg.watchmaster.data.local.entity.CustomListEntity

class CustomListsRepository(
    private val dao: CustomListsDao
) {

    fun getAllCustomLists() = dao.getAllCustomLists()

    suspend fun insertCustomListItem(item: CustomListEntity) = dao.insertCustomListItem(item)

    suspend fun deleteCustomList(id: Long) = dao.deleteCustomList(id)

    suspend fun getCustomListById(id: Long): CustomListEntity {
        return dao.getCustomListById(id)
    }

    suspend fun updateCustomList(
        id: Long,
        name: String,
        description: String,
        movieIds: List<Long>,
        icon: MediaListsIcons
    ) {
        dao.updateCustomList(id, name, description, movieIds, icon)
    }

    suspend fun setPinned(id: Long, isPinned: Boolean) {
        dao.setPinned(id, isPinned)
    }
}