package com.plcoding.bookpedia.book.presentation.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.plcoding.bookpedia.app.Route
import com.plcoding.bookpedia.book.data.repository.DefaultBookRepository
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//register it in koin module to
class BookDetailViewModel(
    //also comes after at BookDetailScreen 2
    private val bookRepository: BookRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    //later on to
    private val bookId = savedStateHandle.toRoute<Route.BookDetail>().id
    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.onStart {
        fetchBookDescription()
        observeFavoriteStatus()
    }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value

        )

    fun onAction(action: BookDetailCommand){
        when(action){
            is BookDetailCommand.OnSelectedBookChange -> {
                _state.update {
                    it.copy(
                        book = action.book
                    )
                }
            }
            is BookDetailCommand.OnFavoriteClick -> {
                viewModelScope.launch {
                    if(state.value.isFavorite){
                        bookRepository.deleteFromFavorite(bookId)
                    }else{
                        state.value.book?.let { book ->
                            bookRepository.markAsFavorite(book)
                        }
                    }
                }
            }
            else -> Unit
        }
    }

    //comes after databaseImplementation, we check of value change in our db to update UI
    private fun observeFavoriteStatus(){
        bookRepository
            .isBookFavorite(bookId)
            //on every (each)change , we get info and update our state with that info
            .onEach {
                isFavorite ->
                _state.update {
                    it.copy (
                        isFavorite = isFavorite
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    //also comes after at BookDetailScreen 2
    private fun fetchBookDescription(){
        viewModelScope.launch {
            bookRepository
                .getBookDescription(bookId)
                .onSuccess {  description ->
                    _state.update {
                        it.copy(
                            book = it.book?.copy(
                                description = description
                            ),
                            isLoading = false
                        ) }
                }
        }
    }
}