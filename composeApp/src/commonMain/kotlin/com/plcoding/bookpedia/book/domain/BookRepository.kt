package com.plcoding.bookpedia.book.domain

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result

interface BookRepository {
    suspend fun searchBooks(query :  String): Result<List<Book>, DataError.Remote>
    //also comes after at BookDEtailScreen2
    suspend fun getBookDescription(bookId : String): Result<String?, DataError> //we don't use DataError.Remote since fetching the response can be from our local repository
}