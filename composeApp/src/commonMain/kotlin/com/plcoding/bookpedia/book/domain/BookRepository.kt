package com.plcoding.bookpedia.book.domain

import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    //we use mapers here to map book entities and stuff to avoid collision between layers
    suspend fun searchBooks(query :  String): Result<List<Book>, DataError.Remote>
    //also comes after at BookDEtailScreen2
    suspend fun getBookDescription(bookId : String): Result<String?, DataError> //we don't use DataError.Remote since fetching the response can be from our local repository
    //more functions for room later on

    fun getFavoriteBooks(): Flow<List<Book>>
    //observer whether the book status as a favorite changes
   fun isBookFavorite(id : String): Flow<Boolean>
   //empty result is a type alias for Result with result unit
    suspend fun markAsFavorite(book : Book) : EmptyResult<DataError.Local> //the error could be that the database is full
    suspend fun deleteFromFavorite(id : String)
}