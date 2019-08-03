package yoyo.app.android.com.yoyoapp.trip.api


import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("username")
    val username: String,
    @SerializedName("password")
    val password: String
)