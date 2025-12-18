package com.plcoding.bookpedia.app

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object BookGraph: Route
    //serializable since CMP should figure out a way to add this and pass it
    @Serializable
    data object BookList:Route
    //a data object since we need the particular id of the book we clicked
    @Serializable
    data class BookDetail(val id: String): Route
}