package com.plcoding.bookpedia.book.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBookDao {
    @Upsert
    suspend fun upsert(book : BookEntity)

    //we have a query that might change and we need to observer it for it to give the latest query so we use flow
    //this isn't a suspend function because flow is async by default
    @Query("SELECT * FROM BookEntity")
    fun getFavoriteBooks(): Flow<List<BookEntity>>

    //get all books where our id matches to the one given
    @Query("SELECT * FROM BookEntity where id = :id")
    suspend fun getFavoriteBook(id: String): BookEntity?

    @Query("DELETE  FROM BookEntity where id = :id")
    suspend fun deleteFavoriteBook(id: String)

}