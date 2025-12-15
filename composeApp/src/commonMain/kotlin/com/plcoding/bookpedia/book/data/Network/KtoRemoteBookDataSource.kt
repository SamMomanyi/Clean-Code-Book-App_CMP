package com.plcoding.bookpedia.book.data.Network

import com.plcoding.bookpedia.book.domain.Book
import com.plcoding.bookpedia.core.data.safeCall
import com.plcoding.bookpedia.core.domain.DataError
import com.plcoding.bookpedia.core.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

private const val BASE_URL = "https://openlibrary.org"

class KtoRemoteBookDataSource(private val httpClient : HttpClient) {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null) : Result<List<Book>, DataError.Remote> {
        return safeCall{
            httpClient.get(
                urlString = "$BASE_URL/search.json"
            ){
                parameter("q",query)
                parameter("limit",resultLimit)
                parameter("language","eng")
                //other fields which we already specified
                parameter("fields","key,title,language,cover_i,author_key,author_name,cover_edition_key,first_publish_year,ratings_average,ratings_count,number_of_pages_median,edition_count")
            }
        }
    }
}