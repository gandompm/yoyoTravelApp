package yoyo.app.android.com.yoyoapp.trip.api


import com.google.gson.annotations.SerializedName

data class ResponseSetProfilePicture(
    @SerializedName("profile_thumbnail_picture")
    val profileThumbnailPicture: String? = ""
)