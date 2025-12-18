package com.plcoding.bookpedia

import androidx.compose.runtime.Composable
import com.plcoding.bookpedia.book.presentation.book_list.BookListScreen
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.presentation.book_list.BookListState

//dummylist of boooks to preview
private val books = (1..100).map{
    Book(
        id = it.toString(),
        title = "Book $it",
        imageUrl = "https://test.com",
        authors = listOf("Phillipp Lackner"),
        description = "Description $it",
        languages = emptyList(),
        firstPublishYear = null,
        averageRating = 4.67854,
        ratingCount = 5,
        numPages = 100,
        numEditions = 3
    )
}

@Preview
@Composable
private fun BookListScreenPreview(){
    BookListScreen(
        state = BookListState(
            searchResults = books
        ),
        onAction = {}
    )
}

