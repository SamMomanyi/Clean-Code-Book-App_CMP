package com.plcoding.bookpedia.book.data.repository

import androidx.sqlite.SQLiteException
import com.plcoding.bookpedia.book.data.Network.RemoteBookDataSource
import com.plcoding.bookpedia.book.data.database.FavoriteBookDao
import com.plcoding.bookpedia.book.data.mappers.toBook
import com.plcoding.bookpedia.book.data.mappers.toBookEntity
import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.book.domain.BookRepository
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.EmptyResult
import com.plcoding.bookpedia.core.domain.map
import com.plcoding.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//function of the repository is to cooridnate the access between multiple data sources e.g favoriteBookDao and RemoteBookDataSource
class DefaultBookRepository(
    private val remoteBookDataSource: RemoteBookDataSource,
    private val favoriteBookDao: FavoriteBookDao
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

    //also comes after on
    override suspend fun getBookDescription(bookId: String): Result<String?, DataError> {
        //later on after room setup
        //we check if the result is in our local database, then we don't need to make the API call
        val localResult = favoriteBookDao.getFavoriteBook(bookId)


        return if(localResult == null) {
            remoteBookDataSource
                .getBookDetails(bookId)
                .map { it.description }
        } else {
            Result.Success(localResult.description)
        }
    }

    override fun getFavoriteBooks(): Flow<List<Book>> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map {
                    bookEntities ->
                bookEntities.map { it.toBook() }
            }
    }

    override fun isBookFavorite(id: String): Flow<Boolean> {
        return favoriteBookDao
            .getFavoriteBooks()
            .map{
                bookEntites ->
                bookEntites.any{
                    it.id == id
                }
            }
    }

    override suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local> {
        return try{
            favoriteBookDao.upsert(book.toBookEntity())
            Result.Success(Unit)
        } catch(e: SQLiteException){
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override suspend fun deleteFromFavorite(id: String) {
        return favoriteBookDao.deleteFavoriteBook(id)
    }
}