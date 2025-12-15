package com.plcoding.bookpedia.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    //this is what the api will respond with , thich then contains a list of searchedbookdto
    @SerialName("docs")val results: List<SearchedBookDto>
)
