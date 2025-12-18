package com.plcoding.bookpedia.book.presentation.book_detail

import coil3.compose.AsyncImagePainter
import com.plcoding.bookpedia.book.domain.Book

data class BookDetailState(
    val isLoading: Boolean = true,
    val isFavorite: Boolean = false,
    //we need to observe and also send the selectedBook here to ensure the two pages are in sync
    val book: Book? = null
)
