package yoyo.app.android.com.yoyoapp.trip.api

import com.fasterxml.jackson.annotation.*
import com.fasterxml.jackson.core.*
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.node.*
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.module.kotlin.*

private val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

data class ProfileResponse(
    val username: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val email: String? = null,

    @get:JsonProperty("phone_number") @field:JsonProperty("phone_number")
    val phoneNumber: String? = null,

    @get:JsonProperty("profile_thumbnail_picture") @field:JsonProperty("profile_thumbnail_picture")
    val profileThumbnailPicture: String? = null,

    @get:JsonProperty("profile_original_picture") @field:JsonProperty("profile_original_picture")
    val profileOriginalPicture: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<ProfileResponse>(json)
    }
}