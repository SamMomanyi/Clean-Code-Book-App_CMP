package com.plcoding.bookpedia.book.data.repository

import com.plcoding.bookpedia.book.data.Network.RemoteBookDataSource
import com.plcoding.bookpedia.book.data.mappers.toBook
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.map
import com.plcoding.bookpedia.core.domain.Result

class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource
): BookRepository {
    override suspend fun searchBooks(query :  String) : Result<List<Book>, DataError.Remote> {
        //we get a type mismatch error since we expect a list of books but out abstraction interface RemoteBookDataSource returns a searchDto
        //so we need to get the search dto and return it into a usable format
        //
        return remoteBookDataSource
            .searchBooks(query)
            .map {
                dto ->
                dto.results.map{it.toBook() }
            }
    }
}