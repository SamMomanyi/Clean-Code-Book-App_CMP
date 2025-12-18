package com.plcoding.bookpedia.book.data.Network

import com.plcoding.bookpedia.book.data.dto.BookWorkDto
import com.plcoding.bookpedia.book.data.dto.SearchResponseDto
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ):Result<SearchResponseDto,DataError.Remote>

    //this happens after creation of BookWorkDto at Book Detail Screen chapter 2
    suspend fun getBookDetails(bookWorkId : String): Result<BookWorkDto, DataError.Remote>
}