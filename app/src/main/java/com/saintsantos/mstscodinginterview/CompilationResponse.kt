package com.saintsantos.mstscodinginterview

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompilationResponse(
    @Json(name = "field_name") val fieldName: String? = null
)
