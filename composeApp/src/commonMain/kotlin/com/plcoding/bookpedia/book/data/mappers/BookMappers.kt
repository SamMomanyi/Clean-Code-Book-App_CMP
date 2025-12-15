package com.plcoding.bookpedia.book.data.mappers

import com.plcoding.bookpedia.book.data.dto.SearchedBookDto
import com.plcoding.bookpedia.book.domain.Book

//these aer the mappers , maps one object to another
fun SearchedBookDto.toBook(): Book{
    return Book(
        id = id,
        title = title,
        // a url to get a bookimage

        imageUrl = if(coverAlternativeKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else if(coverKey != null) {
            // ✅ FIX: Use /id/ for numbers, not /olid/
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        } else {
            null
        },
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0

    )
}