package yoyo.app.android.com.yoyoapp.trip.api


import com.google.gson.annotations.SerializedName

data class RequestSetProfilePicture(
    @SerializedName("profile_picture")
    val profilePicture: String
)