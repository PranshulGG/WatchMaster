package com.pranshulgg.watchmaster.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pranshulgg.watchmaster.core.model.MediaListsIcons
import com.pranshulgg.watchmaster.data.local.entity.CustomListEntity
import kotlinx.coroutines.flow.Flow
import kotlin.collections.forEach

@Dao
interface CustomListsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomListItem(item: CustomListEntity)

    @Query("SELECT * FROM custom_lists")
    fun getAllCustomLists(): Flow<List<CustomListEntity>>

    @Query("DELETE FROM custom_lists WHERE id = :id")
    suspend fun deleteCustomList(id: Long)

    @Query("SELECT * FROM custom_lists WHERE id = :id LIMIT 1")
    suspend fun getCustomListById(id: Long): CustomListEntity


    @Query(
        """
    UPDATE custom_lists 
    SET name = :name,
        description = :description,
        ids = :movieIds,
        icon = :icon
    WHERE id = :id
"""
    )
    suspend fun updateCustomList(
        id: Long,
        name: String,
        description: String,
        movieIds: List<Long>,
        icon: MediaListsIcons
    )


    @Query("UPDATE custom_lists SET isPinned = :isPinned WHERE id = :id")
    suspend fun setPinned(id: Long, isPinned: Boolean)

    @Query("DELETE FROM custom_lists")
    suspend fun clearAll()

    suspend fun insertAll(items: List<CustomListEntity>) {
        items.forEach { insertCustomListItem(it) }
    }

}