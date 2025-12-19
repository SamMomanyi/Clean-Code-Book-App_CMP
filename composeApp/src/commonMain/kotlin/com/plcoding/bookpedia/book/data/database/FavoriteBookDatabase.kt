package com.plcoding.bookpedia.book.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

//check out database migration
//e.g having to increase the version due to changing database schema
@Database(
    entities = [BookEntity::class],
    version = 1
)

//also notify room of the type converters , note we know use the plural annotation
@TypeConverters(
    StringListTypeConverter::class
)
abstract class FavoriteBookDatabase : RoomDatabase() {
    abstract val favoriteBookDao : FavoriteBookDao

    companion object {
        const val DB_NAME = "book.db"
    }
}