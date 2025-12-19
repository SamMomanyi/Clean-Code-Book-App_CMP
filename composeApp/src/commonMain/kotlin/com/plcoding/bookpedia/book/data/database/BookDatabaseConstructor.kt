package com.plcoding.bookpedia.book.data.database

import androidx.room.RoomDatabaseConstructor

//remeber expect means -> we want to have this code in our shared module but it's implementation differs
//the error will be gone when we add this since it tells room to add actual implementation for itself
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object BookDatabaseConstructor : RoomDatabaseConstructor<FavoriteBookDatabase>{
    override fun initialize(): FavoriteBookDatabase
}