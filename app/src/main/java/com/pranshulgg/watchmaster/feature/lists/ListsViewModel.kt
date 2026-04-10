package com.pranshulgg.watchmaster.feature.lists

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranshulgg.watchmaster.core.model.MediaListsIcons
import com.pranshulgg.watchmaster.data.local.entity.CustomListEntity
import com.pranshulgg.watchmaster.data.local.entity.WatchlistItemEntity
import com.pranshulgg.watchmaster.data.repository.CustomListsRepository
import com.pranshulgg.watchmaster.feature.lists.listEntry.ListEntryScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val repo: CustomListsRepository
) : ViewModel() {


    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _uiState = mutableStateOf(ListEntryScreenUiState())
    val uiState: State<ListEntryScreenUiState> = _uiState

    private val _currentList = MutableStateFlow<CustomListEntity?>(null)
    val currentList: StateFlow<CustomListEntity?> = _currentList


    val customLists = repo
        .getAllCustomLists()
        .onEach {
            _isLoading.value = false
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )


    fun saveList(updatingList: Boolean = false, id: Long = -1L) = viewModelScope.launch {

        val item = CustomListEntity(
            name = uiState.value.listName,
            description = uiState.value.listDescription,
            ids = uiState.value.selectedMediaItems.map { it.id },
            icon = uiState.value.listIcon
        )

        if (!updatingList) {
            repo.insertCustomListItem(item)
        } else if (id != -1L) {
            repo.updateCustomList(
                id,
                item.name,
                item.description,
                item.ids,
                item.icon ?: MediaListsIcons.FOLDER
            )
        }
    }

    fun getCustomListById(id: Long) {
        viewModelScope.launch {
            _currentList.value = repo.getCustomListById(id)
        }
    }

    fun setPinned(id: Long, isPinned: Boolean) {
        viewModelScope.launch {
            repo.setPinned(id, isPinned)
        }
    }

    fun delete(id: Long) = viewModelScope.launch {
        repo.deleteCustomList(id)
    }

    fun showCustomListScreenSheet() {
        _uiState.value = _uiState.value.copy(isSheetOpen = true)
    }

    fun hideCustomListScreenSheet() {
        _uiState.value = _uiState.value.copy(isSheetOpen = false)
    }

    fun updateListName(name: String) {
        _uiState.value = _uiState.value.copy(listName = name)
    }

    fun updateListDescription(description: String) {
        _uiState.value = _uiState.value.copy(listDescription = description)
    }

    fun updateListIcon(icon: MediaListsIcons) {
        _uiState.value = _uiState.value.copy(listIcon = icon)
    }


    fun updateList(list: List<WatchlistItemEntity>) {
        _uiState.value = _uiState.value.copy(selectedMediaItems = list)
    }

    fun showSelectListIconSheet() {
        _uiState.value = _uiState.value.copy(isSelectListIconSheetOpen = true)
    }


    fun hideSelectListIconSheet() {
        _uiState.value = _uiState.value.copy(isSelectListIconSheetOpen = false)
    }
}