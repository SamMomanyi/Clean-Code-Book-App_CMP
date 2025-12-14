package com.plcoding.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BookListViewModel : ViewModel() {
    private val _state = MutableStateFlow(BookListState())
    val state = _state.asStateFlow()

    fun onAction(action : BookListCommand){
        when(action){
            is BookListCommand.OnBooKClick -> {

            }
            is BookListCommand.OnSearchQuesryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is BookListCommand.OnTabSelection -> {
                _state.update{
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }

    }
}