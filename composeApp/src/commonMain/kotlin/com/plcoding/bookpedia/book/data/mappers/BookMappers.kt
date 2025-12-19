package com.plcoding.bookpedia.book.data.mappers

import com.plcoding.bookpedia.book.data.database.BookEntity
import com.plcoding.bookpedia.book.data.dto.SearchedBookDto
import com.plcoding.bookpedia.book.domain.Book

//these aer the mappers , maps one object to another
fun SearchedBookDto.toBook(): Book{
    return Book(
        //for the id we don't just wanna take the id but the substring after last and pass in this forward slash
        id = id.substringAfterLast("/"),
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

fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        id  = id,
        title = title,
    description = description,
     imageUrl  = imageUrl,
    languages = languages,
     authors = authors,
    firstPublishYear = firstPublishYear,
      ratingsAverage  = averageRating ,
   ratingsCount = ratingCount,
     numPagesMedian = numPages,
     numEditions = numEditions
    )
}