package com.plcoding.bookpedia.book.data.dto

import io.ktor.util.internal.OpDescriptor
import kotlinx.serialization.Serializable

//this would work by default only for those books who's description is only a string but not for the object one's
//we open serializable brackets this says we will create this serializable where we define our own logic then now say with = ....
@Serializable(with = BookWorkDtoSerializer::class)
data class BookWorkDto(
    val description : String? = null
)
