package com.plcoding.bookpedia.book.presentation.book_detail

import com.plcoding.bookpedia.book.domain.Book

interface BookDetailCommand {
    data object OnBackClick :  BookDetailCommand
    data object OnFavoriteClick : BookDetailCommand
    data class OnSelectedBookChange(val book: Book): BookDetailCommand
}