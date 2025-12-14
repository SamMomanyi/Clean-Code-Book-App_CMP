package com.plcoding.bookpedia.book.presentation.book_list
import com.plcoding.bookpedia.book.domain.Book

sealed interface BookListCommand {
    data class OnSearchQuesryChange(val query : String): BookListCommand
    data class OnBooKClick(val book : Book):BookListCommand
    data class OnTabSelection(val index : Int):BookListCommand
}