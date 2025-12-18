package com.plcoding.bookpedia.book.data.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

//we then pass in the type of class we want to serialize
object BookWorkDtoSerializer: KSerializer<BookWorkDto> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        BookWorkDto::class.simpleName!!
    ) {
        //the field or elemnet we want from API
        //if we wanted more we could add them
        element<String?>("description")
    }

    override fun deserialize(decoder: Decoder): BookWorkDto = decoder.decodeStructure(descriptor) {
        var description: String? = null

        while (true) {
            //this doesn't specify the order of index fields but the order we've specified above i.e index 0 is description
            when (val index = decodeElementIndex(descriptor)) {
                0 -> {
                    val jsonDecoder = decoder as? JsonDecoder ?: throw SerializationException(
                        "This decoder only works with HSON"
                    )
                    val element = jsonDecoder.decodeJsonElement()
                    description = if (element is JsonObject) {
                        decoder.json.decodeFromJsonElement<DescriptionDto>(
                            element = element,
                            deserializer = DescriptionDto.serializer()
                        ).value
                    } else if (element is JsonPrimitive && element.isString) { //if it is not an object
                        element.content

                    } else null
                }

                CompositeDecoder.DECODE_DONE -> break
                else -> throw SerializationException("Unexpected index $index")
            }

        }
        return@decodeStructure BookWorkDto(description)
    }
    //this works for pushing requests
    override fun serialize(encoder: Encoder, value: BookWorkDto) = encoder.encodeStructure( //opposite of decode structure
        descriptor
    ) {
        value.description?.let{
            encodeStringElement(descriptor,0,it)
        }
    }

}