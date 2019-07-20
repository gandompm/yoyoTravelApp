package yoyo.app.android.com.yoyoapp.trip.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

private val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

data class SignInRequest (
    val username: String,
    val password: String
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<SignInRequest>(json)
    }
}