package yoyo.app.android.com.yoyoapp.trip.api


import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("email_or_phone_number")
    val emailOrPassword: String,
    @SerializedName("password")
    val password: String
)