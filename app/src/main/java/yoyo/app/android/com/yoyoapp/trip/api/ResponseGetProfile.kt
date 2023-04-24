package yoyo.app.android.com.yoyoapp.trip.api

import com.google.gson.annotations.SerializedName

data class ResponseGetProfile(
    @SerializedName("emailOrPassword")
    val username: String? = null,
    @SerializedName("firstname")
    val firstname: String? = null,
    @SerializedName("lastname")
    val lastname: String? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("phone_number")
    val phoneNumber: String? = null,
    @SerializedName("profile_thumbnail_picture")
    val profileThumbnailPicture: String? = null,
    @SerializedName("profile_original_picture")
    val profileOriginalPicture: String? = null
)