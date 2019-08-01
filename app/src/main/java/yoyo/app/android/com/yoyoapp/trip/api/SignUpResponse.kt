package yoyo.app.android.com.yoyoapp.trip.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue

private val mapper = jacksonObjectMapper().apply {
    propertyNamingStrategy = PropertyNamingStrategy.LOWER_CAMEL_CASE
    setSerializationInclusion(JsonInclude.Include.NON_NULL)
}

data class SignUpResponse (
    val username: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val email: String? = null,

    @get:JsonProperty("phone_number")@field:JsonProperty("phone_number")
    val phoneNumber: String? = null,

    @get:JsonProperty("profile_thumbnail_picture")@field:JsonProperty("profile_thumbnail_picture")
    val profileThumbnailPicture: String? = null,

    @get:JsonProperty("profile_original_picture")@field:JsonProperty("profile_original_picture")
    val profileOriginalPicture: String? = null,

    val token: String? = null
) {
    fun toJson() = mapper.writeValueAsString(this)

    companion object {
        fun fromJson(json: String) = mapper.readValue<SignUpResponse>(json)
    }


}
