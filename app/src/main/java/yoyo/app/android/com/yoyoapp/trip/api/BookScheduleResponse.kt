package yoyo.app.android.com.yoyoapp.trip.api


import com.google.gson.annotations.SerializedName

data class BookScheduleResponse(
    @SerializedName("payment_url")
    val paymentUrl: String? = null
)